package us.hnry.fancy;

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
import java.util.HashMap;
import java.util.Map;

import us.hnry.fancy.data.DetailAdapter;
import us.hnry.fancy.data.Stock;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<String> keys;
    private Method[] methods;
    Map<String, String> map;

    private ListView mDetailListView;

    private DetailAdapter consumeParcelableStockFromIntent(Stock stock)
            throws InvocationTargetException, IllegalAccessException {

        keys = new ArrayList<>();
        methods = stock.getClass().getMethods();
        map = new HashMap<>();

        for(Method m : methods)
        {
            if(m.getName().startsWith("get"))
            {
                String value = String.valueOf(m.invoke(stock));
                String name = m.getName().substring(3);
                if(!name.equals("Class")) {
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailListView = (ListView) findViewById(R.id.content_detail_list_view);
        Stock fromIntent = getIntent().getParcelableExtra("intent_parcelable_stock");
        if(fromIntent != null) {
            setTitle(fromIntent.getName());
            try {
                DetailAdapter detailAdapter = consumeParcelableStockFromIntent(fromIntent);
                mDetailListView.setAdapter(detailAdapter);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "FAILED!", Toast.LENGTH_SHORT).show();
        }
    }
}
