package us.hnry.fancy;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.deprecated.SearchAdapter;
import us.hnry.fancy.fragments.ThorRetroSearch;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

public class SearchActivity extends AppCompatActivity {

    static final String FRAGMENT_TAG_THOR = "fragment_search_thor";
    static final String FRAGMENT_TAG_SYMBOL = "fragment_search_symbol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        int fragmentSelector = getIntent().getIntExtra(Utility.SEARCH_INTENT, 0);
        switch (fragmentSelector) {
            case Utility.THOR_SEARCH:
                setTitle("Thor Search");
                getFragmentManager().beginTransaction().add(R.id.thor_container, new ThorRetroSearch(), FRAGMENT_TAG_THOR).commit();
                break;
            case Utility.SYMBOL_SEARCH:
                setTitle("Symbol Search");
                getFragmentManager().beginTransaction().add(R.id.thor_container, new SymbolSearch(), FRAGMENT_TAG_SYMBOL).commit();
                break;
        }
    }

    public static class SymbolSearch extends Fragment {

        private TextView mTextView;
        private EditText mEditTextSearch;
        private Button mButtonSearch;
        private ListView mListViewSearch;
        private ArrayList<Stock> mQuotes;
        private Intent mLaunchDetail;
        private InputMethodManager mInputMethodManager;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_search, container, false);
            mTextView = (TextView) layout.findViewById(R.id.search_text_view_warning);
            mTextView.setText("Using Symbol search. To search by company, go back to the main screen and use Thor search.");
            mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
            mEditTextSearch.setHint("Company Symbol");
            mButtonSearch = (Button) layout.findViewById(R.id.search_button);
            mListViewSearch = (ListView) layout.findViewById(R.id.search_list_view);
            mListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new Thread() {
                        @Override
                        public void run() {
                            mLaunchDetail = new Intent(getActivity(), DetailActivity.class);
                            mLaunchDetail.putExtra(Utility.STOCK_INTENT, mQuotes.get(position));
                            startActivity(mLaunchDetail);
                        }
                    }.start();
                }
            });


            mButtonSearch = (Button) layout.findViewById(R.id.search_button);
            mButtonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    mInputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Collecting Results");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    final String userInput = mEditTextSearch.getText().toString();
                    if (!userInput.equals("")) {
                        new Thread() {
                            @Override
                            public void run() {
                                String query = userInput.toUpperCase();
                                QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(query);
                                FetchStockTask task = new FetchStockTask(getActivity());
                                task.execute(queryBuilder.build());
                                try {
                                    mQuotes = task.get();
                                    if (mQuotes.size() == 1) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        });
                                        mLaunchDetail = new Intent(getActivity(), DetailActivity.class);
                                        mLaunchDetail.putExtra(Utility.STOCK_INTENT, mQuotes.get(0));
                                        startActivity(mLaunchDetail);
                                    } else {
                                        ArrayList<Symbol> results = new ArrayList<>();
                                        for (Stock quote : mQuotes) {
                                            String symbol = quote.getSymbol();
                                            String name = quote.getName();
                                            results.add(new Symbol(symbol, name));
                                        }
                                        final SearchAdapter searchAdapter = new SearchAdapter(getActivity(), results);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mListViewSearch.setAdapter(searchAdapter);
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }

                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                    Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }.start();
                    } else {
                        Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
            return layout;
        }
    }
}
