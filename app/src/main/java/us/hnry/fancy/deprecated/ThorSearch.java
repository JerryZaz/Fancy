package us.hnry.fancy.deprecated;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Removed from the SearchActivity class
 */
@Deprecated
public class ThorSearch extends Fragment {

    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private ListView mListViewSearch;
    private ArrayList<Symbol> mResults;
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
        mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
        mButtonSearch = (Button) layout.findViewById(R.id.search_button);
        mListViewSearch = (ListView) layout.findViewById(R.id.search_list_view);
        mListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ProgressDialog listItemClickProgressDialog = new ProgressDialog(getActivity());
                listItemClickProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                listItemClickProgressDialog.setMessage("Processing your request");
                listItemClickProgressDialog.setCancelable(false);
                listItemClickProgressDialog.setIndeterminate(true);
                listItemClickProgressDialog.show();

                new Thread() {
                    @Override
                    public void run() {
                        final FetchStockTask task = new FetchStockTask(getActivity());
                        task.execute(new QuoteQueryBuilder(mResults
                                .get(position)
                                .getSymbol())
                                .build());
                        try {
                            ArrayList<Stock> quotes = task.get();
                            if (quotes != null) {
                                if (quotes.size() > 0) {
                                    final Intent launchDetail = new Intent(getActivity(), DetailActivity.class);
                                    launchDetail.putExtra(Utility.STOCK_INTENT, quotes.get(0));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listItemClickProgressDialog.dismiss();
                                            startActivity(launchDetail);
                                        }
                                    });
                                } else {
                                    SharedPreferences preferences = getActivity().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    Set<String> persistentSymbolsSet =
                                            preferences
                                                    .getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                                            new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
                                    persistentSymbolsSet.add(mResults.get(position).getSymbol());
                                    editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, persistentSymbolsSet);
                                    editor.apply();

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                                            listItemClickProgressDialog.dismiss();
                                        }
                                    });

                                    getActivity().finish();
                                }
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Collecting Results");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        mButtonSearch = (Button) layout.findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                mInputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                final String search = Utility.getStringBeforeBlank(mEditTextSearch.getText().toString());
                mEditTextSearch.setText(search);
                if (!search.equals("")) {
                    progressDialog.show();
                    final ThorSearchTask searchTask = new ThorSearchTask();

                    new Thread() {
                        @Override
                        public void run() {
                            searchTask.execute(search);
                            try {
                                mResults = searchTask.get();
                                if (mResults != null) {
                                    if (mResults.size() > 0) {
                                        final SearchAdapter adapter = new SearchAdapter(getActivity(), mResults);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mListViewSearch.setAdapter(adapter);
                                                progressDialog.dismiss();
                                            }
                                        });

                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        });
                                        Snackbar.make(v, "Your search returned no results.", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                });
                                Snackbar.make(v, "Your search returned no results.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }.start();
                } else {
                    Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return layout;
    }
}