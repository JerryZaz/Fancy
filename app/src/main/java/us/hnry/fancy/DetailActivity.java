package us.hnry.fancy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import us.hnry.fancy.adapters.DetailAdapter;
import us.hnry.fancy.data.Stock;
import us.hnry.fancy.utils.Utility;

public class DetailActivity extends AppCompatActivity {

    Map<String, String> map;
    private ArrayList<String> keys;
    private Method[] methods;
    private ListView mDetailListView;

    private Stock fromIntent;
    private Set<String> mSetOfStocks;
    private boolean isTracked;

    private DetailAdapter consumeParcelableStockFromIntent(Stock stock)
            throws InvocationTargetException, IllegalAccessException {

        keys = new ArrayList<>();
        methods = stock.getClass().getMethods();
        map = new HashMap<>();

        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String value = String.valueOf(m.invoke(stock));
                String name = m.getName().substring(3);
                if (!name.equals("Class")) {
                    map.put(name, value);
                    keys.add(name);
                }
            }
        }

        return new DetailAdapter(this, keys, map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isTracked = false;
        SharedPreferences preferences = getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if(isTracked){
                    mSetOfStocks.remove(fromIntent.getSymbol());
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    isTracked = false;
                    Snackbar.make(view, "Removed from Favorites", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    mSetOfStocks.add(fromIntent.getSymbol());
                    fab.setImageResource(R.drawable.ic_check_circle_white_24dp);
                    isTracked = true;
                    Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_SHORT).show();
                }
                editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, mSetOfStocks);
                editor.apply();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailListView = (ListView) findViewById(R.id.content_detail_list_view);
        fromIntent = getIntent().getParcelableExtra(Utility.STOCK_INTENT);
        if (fromIntent != null) {
            setTitle(fromIntent.getName());
            mSetOfStocks = preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                    new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
            if (mSetOfStocks.contains(fromIntent.getSymbol())) {
                fab.setImageResource(R.drawable.ic_check_circle_white_24dp);
                isTracked = true;
            }
            try {
                DetailAdapter detailAdapter = consumeParcelableStockFromIntent(fromIntent);
                mDetailListView.setAdapter(detailAdapter);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Something went wrong while fetching data", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
