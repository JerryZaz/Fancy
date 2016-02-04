package us.hnry.fancy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import us.hnry.fancy.adapters.SearchAdapter;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.data.Stock;
import us.hnry.fancy.data.Symbol;
import us.hnry.fancy.data.SymbolSearchTask;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

public class SearchActivity extends AppCompatActivity {

    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private ListView mListViewSearch;
    private ArrayList<Symbol> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEditTextSearch = (EditText) findViewById(R.id.search_edit_text);
        mListViewSearch = (ListView) findViewById(R.id.search_list_view);
        mListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final ProgressDialog listItemClickProgressDialog = new ProgressDialog(SearchActivity.this);
                listItemClickProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                listItemClickProgressDialog.setMessage("Processing your request");
                listItemClickProgressDialog.setCancelable(false);
                listItemClickProgressDialog.setIndeterminate(true);
                listItemClickProgressDialog.show();

                new Thread(){
                    @Override
                    public void run() {
                        final FetchStockTask task = new FetchStockTask(SearchActivity.this);
                        task.execute(new QuoteQueryBuilder(mResults
                                .get(position)
                                .getSymbol())
                                .build());
                        try {
                            ArrayList<Stock> quotes = task.get();
                            if (quotes != null) {
                                if (quotes.size() > 0) {
                                    final Intent launchDetail = new Intent(SearchActivity.this, DetailActivity.class);
                                    launchDetail.putExtra(Utility.STOCK_INTENT, quotes.get(0));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listItemClickProgressDialog.dismiss();
                                            startActivity(launchDetail);
                                        }
                                    });
                                } else {
                                    SharedPreferences preferences = getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    Set<String> persistentSymbolsSet =
                                            preferences
                                                    .getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                                            new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
                                    persistentSymbolsSet.add(mResults.get(position).getSymbol());
                                    editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, persistentSymbolsSet);
                                    editor.apply();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SearchActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                            listItemClickProgressDialog.dismiss();
                                        }
                                    });

                                    finish();
                                }
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Collecting Results");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        mButtonSearch = (Button) findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                hideSoftKeyBoard();

                final String search = Utility.getStringBeforeBlank(mEditTextSearch.getText().toString());
                mEditTextSearch.setText(search);
                if (!search.equals("")) {
                    progressDialog.show();
                    final SymbolSearchTask searchTask = new SymbolSearchTask();

                    new Thread() {
                        @Override
                        public void run() {
                            searchTask.execute(search);
                            try {
                                mResults = searchTask.get();
                                if (mResults != null) {
                                    if (mResults.size() > 0) {
                                        final SearchAdapter adapter = new SearchAdapter(SearchActivity.this, mResults);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mListViewSearch.setAdapter(adapter);
                                                progressDialog.dismiss();
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
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
                                runOnUiThread(new Runnable() {
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
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            try {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
