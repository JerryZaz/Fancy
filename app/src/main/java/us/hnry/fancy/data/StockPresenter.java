package us.hnry.fancy.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.data.model.Quote;
import us.hnry.fancy.data.model.SingleQuote;
import us.hnry.fancy.data.model.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 3/20/2016.
 * Presenter class queried by Views to get access to the underlying data
 */
public class StockPresenter {

    public static final String QUOTE_INTENT = "intent_parcelable_quote";
    private static final String PERSISTENT_SYMBOLS_SET = "key.symbols.set";
    // If no user-preferences are found, a query will be built using this defaults
    private static final String[] DEFAULT_SYMBOLS = new String[]{"GOOG", "MSFT", "AAPL", "AMZN",
            "FB", "TSLA", "T", "TMUS", "YHOO", "NFLX"};

    private SharedPreferences mPreferences;
    private Context mContext;
    private PersistentSymbolsChangedListener mSymbolsChangedListener;

    /**
     * Presenter constructor that's only querying data and doesn't need to register listeners
     *
     * @param context Required to access the Shared Preferences
     */
    public StockPresenter(Context context) {
        mContext = context;
    }

    /**
     * Presenter constructor that registers a listener for changes to the Persistent Symbols set
     *
     * @param context  Required to access the Shared Preferences
     * @param listener registers listener to changes in the Persistent Symbols set
     */
    public StockPresenter(Context context, PersistentSymbolsChangedListener listener) {
        mContext = context;
        mSymbolsChangedListener = listener;
    }

    /**
     * Retrieve the Persistent Symbols Set off of the Shared Preferences.
     *
     * @return the Persistent Symbols Set, or a new Set from the Default Symbols
     */
    private Set<String> getPersistentSymbolsSet() {
        mPreferences = mContext.getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);

        return mPreferences.getStringSet(PERSISTENT_SYMBOLS_SET,
                new HashSet<>(Arrays.asList(DEFAULT_SYMBOLS)));
    }

    /**
     * Retrieve the number of symbols being tracked.
     *
     * @return the size of the Persistent Symbols Set.
     */
    private int getPersistentSymbolsSetCount() {
        return getPersistentSymbolsSet().size();
    }

    /**
     * Determine if a certain symbol is being tracked
     *
     * @param symbol being considered
     * @return true if the symbol can be found in the Persistent Symbols Set.
     */
    public boolean isTracked(Symbol symbol) {
        return getPersistentSymbolsSet().contains(symbol.getSymbol());
    }

    /**
     * Adds a parameter symbol to the list of Persistent Symbols
     *
     * @param symbol to be added
     */
    public void addSymbol(Symbol symbol) {
        if (!isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.add(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();

            editor.clear();
            editor.putStringSet(PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();

            if (mSymbolsChangedListener != null) {
                mSymbolsChangedListener.onSymbolAdded(symbol);
            }
        }
    }

    /**
     * Removes the symbol passed on as parameter from the Persistent
     * Symbols set and notifies the registered listener.
     *
     * @param symbol to be removed
     * @return true if the symbol was present and was successfully removed.
     */
    public boolean removeSymbol(Symbol symbol) {
        if (isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.remove(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();

            editor.clear();
            editor.putStringSet(PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();

            if (mSymbolsChangedListener != null) {
                mSymbolsChangedListener.onSymbolRemoved(symbol);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method responsible for refreshing the data displayed on the main screen.
     * It picks up the user's stored data to build the query, if there's none then it builds
     * the query upon the default list of symbols, with which a call to the server will be made
     * to fetch the most up-to-date data.
     */
    public void fetchStockData(final OnNewStockDataRetrieved listener) {
        int countOfSymbols = getPersistentSymbolsSetCount();

        if (countOfSymbols > 0) {
            QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(getSymbolsAsArray());
            String builtQuery = queryBuilder.build();

            if (countOfSymbols > 1) {

                StockService.Implementation.get(BuildConfig.BASE_API_URL)
                        .getQuotes(builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                        .enqueue(new Callback<Quote<List<SingleQuote>>>() {
                            @Override
                            public void onResponse(Call<Quote<List<SingleQuote>>> call, Response<Quote<List<SingleQuote>>> response) {
                                if (response != null) {
                                    Quote<List<SingleQuote>> quote = response.body();
                                    if (quote != null && quote.getQuery() != null) {
                                        List<SingleQuote> asList = quote.getQuery().getResults().getQuote();
                                        listener.newDataAvailable(new ArrayList<>(asList));
                                    } else {
                                        if (response.code() == 401) {
                                            Log.e("getQuotes threw ", "Unauthenticated");
                                        } else if (response.code() >= 400) {
                                            Log.e("getQuotes threw ", "Client error "
                                                    + response.code() + " " + response.message());
                                        }
                                    }
                                } else {
                                    listener.newDataAvailable(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<Quote<List<SingleQuote>>> call, Throwable t) {
                                Log.e("getQuotes threw ", t.getMessage());
                            }
                        });
            } else if (countOfSymbols == 1) {
                StockService.Implementation.get(BuildConfig.BASE_API_URL)
                        .getSingleQuote(builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                        .enqueue(new Callback<Quote<SingleQuote>>() {
                            @Override
                            public void onResponse(Call<Quote<SingleQuote>> call, Response<Quote<SingleQuote>> response) {
                                if (response != null) {
                                    Quote<SingleQuote> single = response.body();
                                    if (single != null && single.getQuery() != null) {
                                        SingleQuote quote = single.getQuery().getResults().getQuote();
                                        ArrayList<SingleQuote> result = new ArrayList<>();
                                        result.add(quote);
                                        listener.newDataAvailable(result);
                                    } else {
                                        if (response.code() == 401) {
                                            Log.e("getQuotes threw ", "Unauthenticated");
                                        } else if (response.code() >= 400) {
                                            Log.e("getQuotes threw ", "Client error "
                                                    + response.code() + " " + response.message());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Quote<SingleQuote>> call, Throwable t) {
                                Log.e("getQuotes threw ", t.getMessage());
                            }
                        });
            }
        }
    }

    /**
     * Fetches the Persistent Symbols Set as an Array of Strings
     *
     * @return Array representation of the Persistent Symbols set.
     */
    private String[] getSymbolsAsArray() {
        return getPersistentSymbolsSet().toArray(new String[getPersistentSymbolsSet().size()]);
    }

    /**
     * Classes that need to react to new data available must implement this interface
     */
    public interface OnNewStockDataRetrieved {
        /**
         * Method to be overridden to retrieve the incoming data
         *
         * @param quotes object containing the new data fetched from the server.
         */
        void newDataAvailable(ArrayList<SingleQuote> quotes);
    }

    /**
     * Classes that implement this interface become reactive to changes to the
     * Persistent Symbols set.
     */
    public interface PersistentSymbolsChangedListener {
        /**
         * React upon a symbol added to the Persistent Symbols Set
         *
         * @param symbol added
         */
        void onSymbolAdded(Symbol symbol);

        /**
         * React upon a symbol removed from the Persistent Symbols Set.
         *
         * @param symbol removed
         */
        void onSymbolRemoved(Symbol symbol);
    }
}
