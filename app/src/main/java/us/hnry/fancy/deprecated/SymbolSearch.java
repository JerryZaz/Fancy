package us.hnry.fancy.deprecated;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Deprecated on 16/02/08 in favor of SymbolRetroSearch
 * which implements Retrofit instead of ASyncTask
 */
@SuppressWarnings("ALL")
@Deprecated
public class SymbolSearch extends Fragment {

    private TextView mTextView;
    private EditText mEditTextSearch;
    private Button mButtonSearch;
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
        View layout = inflater.inflate(R.layout.fragment_search_recycler, container, false);
        mTextView = (TextView) layout.findViewById(R.id.search_text_view_warning);
        mTextView.setText("Using Symbol search. To search by company, go back to the main screen and use Thor search.");
        mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
        mEditTextSearch.setHint("Company Symbol");
        mButtonSearch = (Button) layout.findViewById(R.id.search_button);

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
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
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