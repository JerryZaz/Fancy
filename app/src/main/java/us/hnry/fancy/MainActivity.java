package us.hnry.fancy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.adapters.StockAdapter;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.data.Stock;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mStockListView;
    private ArrayList<Stock> mQuotes;
    private Intent mLaunchDetail;
    private Intent mShareDetail;
    private boolean mShareIntentLoaded;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mShareIntentLoaded = false;
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareIntentLoaded) {
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> appList = packageManager.queryIntentActivities(mShareDetail, PackageManager.MATCH_ALL);
                    if (appList.size() > 0) {
                        fab.setImageResource(R.drawable.ic_search_white_48dp);
                        startActivity(mShareDetail);
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get a reference to the list view
        mStockListView = (ListView) findViewById(R.id.content_main_list_view);

        mStockListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                fab.setImageResource(R.drawable.ic_search_white_48dp);
            }
        });

        mStockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fab.setImageResource(R.drawable.ic_search_white_48dp);
                mLaunchDetail = new Intent(MainActivity.this, DetailActivity.class);
                mLaunchDetail.putExtra(Utility.STOCK_INTENT, mQuotes.get(position));
                startActivity(mLaunchDetail);
            }
        });
        mStockListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                final ProgressDialog shareIntentProgressBar = new ProgressDialog(MainActivity.this);
                shareIntentProgressBar.setMessage("Putting your data in a nice envelope");
                shareIntentProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                shareIntentProgressBar.setCancelable(false);
                shareIntentProgressBar.setIndeterminate(true);
                shareIntentProgressBar.show();

                new Thread(){
                    @Override
                    public void run() {
                        mShareDetail = new Intent(Intent.ACTION_SEND);
                        mShareDetail.setData(Uri.parse("mailto:"));
                        mShareDetail.putExtra(Intent.EXTRA_SUBJECT, "Market Stalker - Shared Stock Data");
                        try {
                            mShareDetail.putExtra(Intent.EXTRA_TEXT, Utility.consumeParcelableStock(mQuotes.get(position)));
                            mShareDetail.setType("message/rfc822");
                            mShareIntentLoaded = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shareIntentProgressBar.dismiss();
                                    Snackbar.make(view, "Now click on the Share button.", Snackbar.LENGTH_LONG).show();
                                    fab.setImageResource(R.drawable.ic_share_white_24dp);
                                }
                            });
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(view, "Something went wrong.", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }.start();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMain();
    }

    public void refreshMain() {
        mShareIntentLoaded = false;
        mShareDetail = null;

        if (preferences == null) {
            preferences = getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Refreshing your data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //Instantiate the async task
        final FetchStockTask task = new FetchStockTask(this);

        final String[] symbolsToQuery =
                Utility.getSymbols(
                        preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS))));

        final QuoteQueryBuilder[] queryBuilder = {null};
        new Thread() {
            @Override
            public void run() {
                queryBuilder[0] = new QuoteQueryBuilder(symbolsToQuery);
                //Execute the task
                task.execute(queryBuilder[0].build());
                try {
                    //Fetch the result of the background thread
                    mQuotes = task.get();
                    if (mQuotes != null) {
                        final StockAdapter stockAdapter = new StockAdapter(MainActivity.this, mQuotes);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Set the adapter to the list view
                                mStockListView.setAdapter(stockAdapter);
                                editor.clear();
                                editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                        new HashSet<>(Arrays.asList(symbolsToQuery)));
                                editor.apply();
                                progressDialog.dismiss();
                            }
                        });

                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_refresh) {
            refreshMain();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tracking) {
            // TODO: something something at some point
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Tap and hold an item on the list", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
