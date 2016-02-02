package us.hnry.fancy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    //    private ArrayList<Stock> mQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEditTextSearch = (EditText) findViewById(R.id.search_edit_text);
        mListViewSearch = (ListView) findViewById(R.id.search_list_view);
        mListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final FetchStockTask task = new FetchStockTask(SearchActivity.this);
                task.execute(new QuoteQueryBuilder(mResults
                        .get(position)
                        .getSymbol())
                        .build());
                try {
                    ArrayList<Stock> quotes = task.get();
                    Log.v(SearchActivity.class.getSimpleName(), String.valueOf(quotes.size()));
                    if (quotes != null) {
                        if (quotes.size() > 0) {
                            Log.v(SearchActivity.class.getSimpleName(), String.valueOf(quotes.size()));
                            Intent launchDetail = new Intent(SearchActivity.this, DetailActivity.class);
                            launchDetail.putExtra(Utility.STOCK_INTENT, quotes.get(0));
                            Log.v(SearchActivity.class.getSimpleName(), String.valueOf(quotes.size()));
                            startActivity(launchDetail);
                        } else {
                            /*Toast.makeText(SearchActivity.this, "Failed", Toast.LENGTH_SHORT).show();*/
                            Snackbar.make(view, "Added", Snackbar.LENGTH_SHORT).show();
                            SharedPreferences preferences = getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            Set<String> persistentSymbolsSet =
                                    preferences
                                            .getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                                    new HashSet<String>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
                            persistentSymbolsSet.add(mResults.get(position).getSymbol());
                            editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, persistentSymbolsSet);
                            editor.apply();
                            finish();
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        mButtonSearch = (Button) findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mEditTextSearch.getText().toString();
                if (!search.equals("")) {
                    SymbolSearchTask searchTask = new SymbolSearchTask();
                    searchTask.execute(search);
                    try {
                        mResults = searchTask.get();
                        if (mResults != null) {
                            SearchAdapter adapter = new SearchAdapter(SearchActivity.this, mResults);
                            mListViewSearch.setAdapter(adapter);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
