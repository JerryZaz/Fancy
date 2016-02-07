package us.hnry.fancy;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.adapters.StockAdapter;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/6/2016.
 *
 */
@Deprecated
public class MainListFragment extends Fragment {

    private FloatingActionButton fab;
    private ListView mStockListView;
    private ArrayList<Stock> mQuotes;
    private Intent mLaunchDetail;
    private Intent mShareDetail;
    private boolean mShareIntentLoaded;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean mShareSnackBarShown;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main_list, container, false);

        mShareIntentLoaded = false;
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareIntentLoaded) {
                    share();
                    fab.setImageResource(R.drawable.ic_search_white_48dp);
                } else {
                    startActivity(new Intent(getActivity(), SearchActivity.class).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH));
                }
            }
        });

        //Get a reference to the list view
        mStockListView = (ListView) layout.findViewById(R.id.content_main_list_view);

        mStockListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!mShareIntentLoaded) {
                    fab.setImageResource(R.drawable.ic_search_white_48dp);
                }
                switch (view.getId()) {
                    case R.id.content_main_list_view:

                        final int lastItem = firstVisibleItem + visibleItemCount;
                        if (totalItemCount > visibleItemCount) {
                            if (lastItem == totalItemCount) {
                                int preLast = lastItem - 1;
                                if (preLast != lastItem) { //to avoid multiple calls for last item
                                    fab.getBackground().setAlpha(40);
                                    if (!mShareSnackBarShown) {
                                        Snackbar.make(view, "Tap and hold and item to Share",
                                                Snackbar.LENGTH_LONG).show();
                                        mShareSnackBarShown = true;
                                    }
                                }
                            } else {
                                fab.getBackground().setAlpha(255);
                            }
                        }

                }
            }
        });

        mStockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fab.setImageResource(R.drawable.ic_search_white_48dp);
                mLaunchDetail = new Intent(getActivity(), DetailActivity.class);
                mLaunchDetail.putExtra(Utility.STOCK_INTENT, mQuotes.get(position));
                startActivity(mLaunchDetail);
            }
        });
        mStockListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                final ProgressDialog shareIntentProgressBar = new ProgressDialog(getActivity());
                shareIntentProgressBar.setMessage("Putting your data in a nice envelope");
                shareIntentProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                shareIntentProgressBar.setCancelable(false);
                shareIntentProgressBar.setIndeterminate(true);
                shareIntentProgressBar.show();

                new Thread() {
                    @Override
                    public void run() {
                        mShareDetail = new Intent(Intent.ACTION_SEND);
                        mShareDetail.setData(Uri.parse("mailto:"));
                        mShareDetail.putExtra(Intent.EXTRA_SUBJECT, "Market Stalker - Shared Stock Data");
                        try {
                            mShareDetail.putExtra(Intent.EXTRA_TEXT, Utility.consumeParcelableStock(mQuotes.get(position)));
                            mShareDetail.setType("message/rfc822");
                            mShareIntentLoaded = true;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shareIntentProgressBar.dismiss();
                                    fab.getBackground().setAlpha(255);
                                    Snackbar.make(view, "Now click on the Share button.", Snackbar.LENGTH_LONG).show();
                                    fab.setImageResource(R.drawable.ic_share_white_24dp);
                                }
                            });
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
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

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMain();
    }

    public void refreshMain() {
        mShareSnackBarShown = false;
        mShareIntentLoaded = false;
        mShareDetail = null;

        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Refreshing your data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //Instantiate the async task
        final FetchStockTask task = new FetchStockTask(getActivity());

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
                        final StockAdapter stockAdapter = new StockAdapter(getActivity(), mQuotes);

                        getActivity().runOnUiThread(new Runnable() {
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

    private void share() {
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mShareDetail, PackageManager.MATCH_ALL);
        if (appList.size() > 0) {
            startActivity(mShareDetail);
        }
    }
}

