package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.R;
import us.hnry.fancy.SearchActivity;
import us.hnry.fancy.adapters.RetroQuoteRecycler;
import us.hnry.fancy.data.StockService.SAPI;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/7/2016.
 * Spin of MainFragment implementing Retrofit instead of ASyncTask
 */
public class MainRetroFragment extends Fragment {
    final String BASE_URL = BuildConfig.BASE_API_URL;
    final String ENV = BuildConfig.ENV;
    final String FORMAT = "json";
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private ArrayList<Quote.SingleQuote> mQuotes;
    private RetroQuoteRecycler mAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String mBuiltQuery;
    private Call<Quote> call;

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

        mAdapter = new RetroQuoteRecycler(mQuotes);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragment_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
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


        final String[] symbolsToQuery =
                Utility.getSymbols(
                        preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS))));
        QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbolsToQuery);

        mBuiltQuery = queryBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SAPI sapi = retrofit.create(SAPI.class);
        call = sapi.getQuotes(mBuiltQuery, ENV, FORMAT);
        call.enqueue(new Callback<Quote>() {
                         @Override
                         public void onResponse(Response<Quote> response) {
                             try {
                                 List<Quote.SingleQuote> asList = response.body().query.results.getQuote();
                                 mQuotes = new ArrayList<>(asList);
                                 mAdapter.swapList(mQuotes);
                             } catch (NullPointerException e) {
                                 Log.v("Catch", "Reached.");
                                 if (response.code() == 401) {
                                     Log.e("getQuotes threw ", "Unauthenticated");
                                 } else if (response.code() >= 400) {
                                     Log.e("getQuotes threw ", "Client error "
                                             + response.code() + " " + response.message());
                                 }

                             } finally {
                                 progressDialog.dismiss();
                             }
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Log.e("getQuotes threw ", t.getMessage());
                         }
                     }
        );

    }
}
