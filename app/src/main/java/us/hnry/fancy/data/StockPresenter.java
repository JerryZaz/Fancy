package us.hnry.fancy.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Quote.SingleQuote;
import us.hnry.fancy.models.Single;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 3/20/2016.
 */
public class StockPresenter {

    public static final String PERSISTENT_SYMBOLS_SET = "key.symbols.set";

    public static final String QUOTE_INTENT = "intent_parcelable_quote";

    // If no user-preferences are found, a query will be build using this defaults
    public static final String[] DEFAULT_SYMBOLS = new String[]{"GOOG", "MSFT", "AAPL", "AMZN",
            "FB", "TSLA", "T", "TMUS", "YHOO", "NFLX"};

    private SharedPreferences mPreferences;
    private Context mContext;
    private PersistentSymbolsChangedListener mListener;

    public StockPresenter(Context context) {
        mContext = context;
    }

    public StockPresenter(Context context, PersistentSymbolsChangedListener listener) {
        mContext = context;
        mListener = listener;
    }

    private Set<String> getPersistentSymbolsSet() {
        mPreferences = mContext.getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);

        return mPreferences.getStringSet(PERSISTENT_SYMBOLS_SET,
                new HashSet<>(Arrays.asList(DEFAULT_SYMBOLS)));
    }

    private int getPersistentSymbolsSetCount() {
        return getPersistentSymbolsSet().size();
    }

    public boolean isTracked(Symbol symbol) {
        return getPersistentSymbolsSet().contains(symbol.getSymbol());
    }

    public boolean addSymbol(Symbol symbol) {
        if (!isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.add(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.putStringSet(PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();
            if (mListener != null) {
                mListener.onSymbolAdded(symbol);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean removeSymbol(Symbol symbol) {
        if (isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.remove(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.putStringSet(PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();
            if (mListener != null) {
                mListener.onSymbolRemoved(symbol);
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<SingleQuote> fetchStockData(final OnNewStockDataRetrieved listener) {
        int countOfSymbols = getPersistentSymbolsSetCount();

        if (countOfSymbols > 0) {
            QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(getSymbolsAsArray());
            String builtQuery = queryBuilder.build();

            if (countOfSymbols > 1) {

                StockService.Implementation.get(BuildConfig.BASE_API_URL)
                        .getQuotes(builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                        .enqueue(new Callback<Quote>() {
                            @Override
                            public void onResponse(Response<Quote> response) {
                                try {
                                    List<SingleQuote> asList = response.body().query.results.getQuote();
                                    listener.newDataAvailable(new ArrayList<>(asList));
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
            if (countOfSymbols == 1) {
                StockService.Implementation.get(BuildConfig.BASE_API_URL)
                        .getSingleQuote(builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                        .enqueue(new Callback<Single>() {
                            @Override
                            public void onResponse(Response<Single> response) {
                                try {
                                    SingleQuote quote = response.body().query.results.getQuote();
                                    ArrayList<SingleQuote> result = new ArrayList<>();
                                    result.add(quote);
                                    listener.newDataAvailable(result);
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
        return new ArrayList<>();
    }

    private String[] getSymbolsAsArray() {
        return getPersistentSymbolsSet().toArray(new String[getPersistentSymbolsSet().size()]);
    }

    public interface OnNewStockDataRetrieved {
        void newDataAvailable(ArrayList<SingleQuote> quotes);
    }

    public interface PersistentSymbolsChangedListener {
        void onSymbolAdded(Symbol symbol);

        void onSymbolRemoved(Symbol symbol);
    }
}
