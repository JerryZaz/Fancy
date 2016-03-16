package us.hnry.fancy.deprecated;

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
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.R;
import us.hnry.fancy.SearchActivity;
import us.hnry.fancy.adapters.RetroQuoteRecycler;
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/7/2016.
 * Spin of MainFragment implementing Retrofit instead of ASyncTask
 */

@SuppressWarnings("ALL")
@Deprecated
public class MainRetroFragment extends Fragment {
    final String BASE_URL = BuildConfig.BASE_API_URL;
    final String ENV = BuildConfig.ENV;
    final String FORMAT = "json";

    private ArrayList<Quote.SingleQuote> mQuotes;
    private RetroQuoteRecycler mAdapter;
    private SharedPreferences preferences;
    private String mBuiltQuery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main_recycler, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.search_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH));
            }
        });

        mAdapter = new RetroQuoteRecycler(mQuotes, getActivity());
        RecyclerView mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragment_main_recycler_view);
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

    /**
     * Method responsible for refreshing the data displayed on the main screen.
     * It picks up the user's stored data to build the query, if there's none then it builds
     * the query upon the default list of symbols, with which a call to the server will be made
     * to fetch the most up-to-date data.
     */
    public void refreshMain() {
        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Refreshing your data");
        progressDialog.setMessage("We're almost there!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        // Query build upon the user's list of tracked companies, or the default sef it no
        // user data is found.
        final String[] symbolsToQuery =
                Utility.getSymbols(
                        preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS))));
        QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbolsToQuery);
        mBuiltQuery = queryBuilder.build();

        //Call to the service to make an HTTP request to the server
        Call<Quote> call = StockService.Implementation.get(BuildConfig.BASE_API_URL)
                .getQuotes(mBuiltQuery, ENV, FORMAT);

        // Execute the request asynchronously with a callback listener to fetch the
        // response or the error message (if any) while talking to the server,
        // creating the request, or processing the response.
        call.enqueue(new Callback<Quote>() {

                         /**
                          * From the interface: Invoked for a received HTTP response.
                          * @param response call .isSuccess to determine if the response indicates
                          *                 success.
                          */
                         @Override
                         public void onResponse(Response<Quote> response) {
                             try {
                                 // Dig into the response, which holds an instance of the Quote
                                 // model class, to fetch the actual Quote.
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

                             // If the user is only tracking one symbol, the previous operation
                             // will fail because the server will respond with an object
                             // instead of the expected array, so the network request must be
                             // handled with a different endpoint that expects an object
                             if(symbolsToQuery.length == 1){
                                 // This IF is so that the server is not queried twice in the
                                 // event that the query is actually malformed
                                 progressDialog.show();

                                 // We already have an instance of the service, we'll use it
                                 // to make another HTTP request, this time using a different
                                 // Endpoint.
                                 Call<Single> call = StockService.Implementation.get(BuildConfig.BASE_API_URL)
                                         .getSingleQuote(mBuiltQuery, ENV, FORMAT);

                                 // Executing asynchronously
                                 call.enqueue(new Callback<Single>() {
                                     @Override
                                     public void onResponse(Response<Single> response) {
                                         try {
                                             Quote.SingleQuote quote = response.body().query.results
                                                     .getQuote();
                                             mQuotes = new ArrayList<>();
                                             mQuotes.add(quote);
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
                                         progressDialog.dismiss();
                                     }
                                 });
                             }
                         }
                     }
        );
    }
}
