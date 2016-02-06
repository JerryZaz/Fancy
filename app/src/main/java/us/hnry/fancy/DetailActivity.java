package us.hnry.fancy;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import us.hnry.fancy.adapters.DetailRecycler;
import us.hnry.fancy.data.Stock;
import us.hnry.fancy.utils.Utility;

public class DetailActivity extends AppCompatActivity {

    static final String FRAGMENT_TAG_DETAIL = "fragment_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.detail_container,
                        new DetailFragment(),
                        FRAGMENT_TAG_DETAIL)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static class DetailFragment extends Fragment {
        Map<String, String> map;
        private ArrayList<String> keys;
        private Method[] methods;
        private RecyclerView mDetailRecyclerView;
        private FloatingActionButton fab;

        private Stock fromIntent;
        private Set<String> mSetOfStocks;
        private boolean isTracked;

        private ProgressDialog mProgressDialog;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Fetching data");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_detail, container, false);
            isTracked = false;
            SharedPreferences preferences = getActivity().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();

            fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    if (isTracked) {
                        mSetOfStocks.remove(fromIntent.getSymbol());
                        fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                        isTracked = false;
                        Snackbar.make(view, "Removed from Favorites", Snackbar.LENGTH_SHORT).show();
                    } else {
                        mSetOfStocks.add(fromIntent.getSymbol());
                        fab.setImageResource(R.drawable.ic_check_circle_white_24dp);
                        isTracked = true;
                        Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_SHORT).show();
                    }
                    editor.clear();
                    editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, mSetOfStocks);
                    editor.apply();
                }
            });

            mDetailRecyclerView = (RecyclerView) layout.findViewById(R.id.content_detail_list_view);
            mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            fromIntent = getActivity().getIntent().getParcelableExtra(Utility.STOCK_INTENT);
            if (fromIntent != null) {
                getActivity().setTitle(fromIntent.getName());
                mSetOfStocks = preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                        new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
                if (mSetOfStocks.contains(fromIntent.getSymbol())) {
                    fab.setImageResource(R.drawable.ic_check_circle_white_24dp);
                    isTracked = true;
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            final DetailRecycler detailAdapter = consumeParcelableStockFromIntent(fromIntent);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mDetailRecyclerView.setAdapter(detailAdapter);
                                    mProgressDialog.dismiss();
                                }
                            });
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            } else {
                Toast.makeText(getActivity(),
                        "Selection returned invalid results from the server.",
                        Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            return layout;
        }

        private DetailRecycler consumeParcelableStockFromIntent(Stock stock)
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
            return new DetailRecycler(getActivity(), keys, map);
        }
    }
}
