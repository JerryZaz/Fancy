package us.hnry.fancy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import us.hnry.fancy.fragments.PersistentSymbolsChangedListener;
import us.hnry.fancy.network.model.Symbol;

/**
 * Created by Henry on 3/20/2016.
 * Presenter class queried by Views to get access to the underlying data
 */
public class SymbolsHelper {

    private SharedPreferences mPreferences;
    private Context mContext;

    @Nullable
    private PersistentSymbolsChangedListener mSymbolsChangedListener;

    /**
     * Presenter constructor that registers a listener for changes to the Persistent Symbols set
     *
     * @param context  Required to access the Shared Preferences
     * @param listener registers listener to changes in the Persistent Symbols set
     */
    public SymbolsHelper(Context context, @Nullable PersistentSymbolsChangedListener listener) {
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

        return mPreferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
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
            editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, persistentSymbols);
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
     */
    public void removeSymbol(Symbol symbol) {
        if (isTracked(symbol)) {
            Set<String> persistentSymbols = getPersistentSymbolsSet();
            persistentSymbols.remove(symbol.getSymbol());
            SharedPreferences.Editor editor = mPreferences.edit();

            editor.clear();
            editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, persistentSymbols);
            editor.apply();

            if (mSymbolsChangedListener != null) {
                mSymbolsChangedListener.onSymbolRemoved(symbol);
            }
        }
    }
}
