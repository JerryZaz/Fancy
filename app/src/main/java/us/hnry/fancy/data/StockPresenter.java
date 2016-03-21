package us.hnry.fancy.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import us.hnry.fancy.models.Symbol;
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

    public StockPresenter(Context context, PersistentSymbolsChangedListener listener){
        mContext = context;
        mListener = listener;
    }

    private Set<String> getPersistentSymbolsSet() {
        mPreferences = mContext.getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);

        return mPreferences.getStringSet(PERSISTENT_SYMBOLS_SET,
                new HashSet<>(Arrays.asList(DEFAULT_SYMBOLS)));
    }

    public boolean addSymbol(Symbol symbol) {
        if (!isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.add(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.putStringSet(PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();
            mListener.onSymbolAdded(symbol);
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
            mListener.onSymbolRemoved(symbol);
            return true;
        } else {
            return false;
        }
    }

    public boolean isTracked(Symbol symbol) {
        return getPersistentSymbolsSet().contains(symbol.getSymbol());
    }

    public interface PersistentSymbolsChangedListener{
        void onSymbolAdded(Symbol symbol);
        void onSymbolRemoved(Symbol symbol);
    }
}
