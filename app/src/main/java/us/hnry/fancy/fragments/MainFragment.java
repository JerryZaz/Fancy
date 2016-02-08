package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.R;
import us.hnry.fancy.SearchActivity;
import us.hnry.fancy.adapters.StockRecycler;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/6/2016.
 */
public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private ArrayList<Stock> mQuotes;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main_recycler, container, false);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH));
            }
        });

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragment_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMain();
    }

    public void refreshMain() {
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
                        final StockRecycler adapter = new StockRecycler(mQuotes, getActivity());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Set the adapter to the list view
                                mRecyclerView.setAdapter(adapter);
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
}
