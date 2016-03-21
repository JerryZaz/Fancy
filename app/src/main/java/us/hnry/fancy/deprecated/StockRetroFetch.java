package us.hnry.fancy.deprecated;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.models.Quote;

/**
 * Created by Henry on 2/7/2016.
 * Test implementation of Retrofit
 * Functionality moved to the Fragment because it was causing
 * the retrofit task to reach it's end with null data
 */
@SuppressWarnings("ALL")
@Deprecated
public class StockRetroFetch {
    final String BASE_URL = BuildConfig.BASE_API_URL;
    final String ENV = BuildConfig.ENV;
    final String FORMAT = "json";
    private String mBuiltQuery;
    private Call<Quote> call;

    private ArrayList<Quote.SingleQuote> mQuotes = new ArrayList<>();

    public StockRetroFetch(String query) {
        mBuiltQuery = query;
    }

    public ArrayList<Quote.SingleQuote> execute() {

        call = StockService.Implementation.get(BASE_URL)
                .getQuotes(mBuiltQuery, ENV, FORMAT);
        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Response<Quote> response) {
                try {
                    List<Quote.SingleQuote> asList = response.body().query.results.getQuote();
                    mQuotes = new ArrayList<>(asList);
                } catch (NullPointerException e){
                    Log.v("Catch", "Reached.");
                    if (response.code() == 401) {
                        Log.e("getQuotes threw ", "Unauthenticated");
                    } else if (response.code() >= 400) {
                        Log.e("getQuotes threw ", "Client error "
                                + response.code() + " " + response.message());
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("getQuotes threw ", t.getMessage());
            }
        });

        return mQuotes;
    }

}
