package us.hnry.fancy.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/17/2016.
 * Service to update the data on the main screen automatically
 */
public class Refresh extends Service implements RefresherControls {

    private static final long MIN_UPDATE_TIME = 1000 * 60;
    private static final String LOG_TAG = Refresh.class.getSimpleName();

    private final IBinder mBinder = new LocalBinder();
    private Thread fetcher;
    private volatile boolean isRunning;
    private UpdateListener mListener;
    private SharedPreferences preferences;

    private ArrayList<Quote.SingleQuote> mQuotes;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {

                refreshMain();
                long futureTime = System.currentTimeMillis() + MIN_UPDATE_TIME;
                while (System.currentTimeMillis() < futureTime)
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                            Log.v(LOG_TAG, "Waiting");
                        } catch (Exception ignored) {
                        }
                    }
            }
            try {
                fetcher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stop();
        return super.onUnbind(intent);
    }

    public void terminate() {
        isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start(new UpdateListener() {
            @Override
            public void onUpdate(ArrayList<Quote.SingleQuote> newData) {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Utility.BROADCAST);
                broadcastIntent.putExtra(Utility.QUOTE_INTENT, newData);
                sendBroadcast(broadcastIntent);
            }
        });

        fetcher = new Thread(mRunnable);
        fetcher.start();

        return Service.START_STICKY;
    }

    @Override
    public void start() {
        if (isRunning) return;
        isRunning = true;
    }

    @Override
    public void stop() {
        if (isRunning) {
            isRunning = false;
            mListener = null;
        }
    }

    @Override
    public void start(UpdateListener listener) {
        start();
        mListener = listener;
    }

    /**
     * Method responsible for refreshing the data displayed on the main screen.
     * It picks up the user's stored data to build the query, if there's none then it builds
     * the query upon the default list of symbols, with which a call to the server will be made
     * to fetch the most up-to-date data.
     */


    public void refreshMain() {
        final String BASE_URL = BuildConfig.BASE_API_URL;
        final String ENV = BuildConfig.ENV;
        final String FORMAT = "json";

        String mBuiltQuery;


        Log.v(LOG_TAG, "RefreshMain called");
        if (preferences == null) {
            preferences = getApplicationContext().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
        }
        // Query build upon the user's list of tracked companies, or the default sef it no
        // user data is found.
        final String[] symbolsToQuery =
                Utility.getSymbols(
                        preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                                new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS))));
        QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbolsToQuery);
        mBuiltQuery = queryBuilder.build();

        // Get an instance of Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Use the retrofit object to generate an implementation of the
        // StockAPI interface.
        final StockService.SAPI sapi = retrofit.create(StockService.SAPI.class);

        if (symbolsToQuery.length > 1) {
            //Call to the service to make an HTTP request to the server
            Call<Quote> call = sapi.getQuotes(mBuiltQuery, ENV, FORMAT);

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
                        //mAdapter.swapList(mQuotes);
                        mListener.onUpdate(mQuotes);
                        Log.v(LOG_TAG, "Refreshed");

                    } catch (NullPointerException e) {
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
        }
        // If the user is only tracking one symbol, the previous operation
        // will fail because the server will respond with an object
        // instead of the expected array, so the network request must be
        // handled with a different endpoint that expects an object
        if (symbolsToQuery.length == 1) {
            // This IF is so that the server is not queried twice in the
            // event that the query is actually malformed


            // We already have an instance of the service, we'll use it
            // to make another HTTP request, this time using a different
            // Endpoint.
            Call<Single> call = sapi.getSingleQuote(mBuiltQuery, ENV, FORMAT);

            // Executing asynchronously
            call.enqueue(new Callback<Single>() {
                @Override
                public void onResponse(Response<Single> response) {
                    try {
                        Quote.SingleQuote quote = response.body().query.results
                                .getQuote();
                        mQuotes = new ArrayList<>();
                        mQuotes.add(quote);
                        //mAdapter.swapList(mQuotes);
                        mListener.onUpdate(mQuotes);
                        Log.v(LOG_TAG, "Refreshed");
                    } catch (NullPointerException e) {
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
        }
    }

    public class LocalBinder extends Binder {
        public Refresh getService() {
            return Refresh.this;
        }
    }
}

