package us.hnry.fancy.utils

import android.content.Context
import us.hnry.fancy.fragments.PersistentSymbolsChangedListener
import us.hnry.fancy.network.model.Symbol
import java.util.*
import kotlin.collections.HashSet

/**
 * @author Henry
 * 10/8/2017
 */
class SymbolsHelper(private val context: Context, private val listener: PersistentSymbolsChangedListener) {
    private val sharedPreferences by lazy { context.getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE) }

    private fun getPersistentSymbolsSet(): MutableSet<String> {
        return HashSet(sharedPreferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                HashSet(Arrays.asList(*Utility.DEFAULT_SYMBOLS))))
    }

    fun getPersistentSymbolsSetAsArray(): Array<String> {
        return getPersistentSymbolsSet().toTypedArray()
    }

    fun isTracked(symbol: Symbol): Boolean {
        return getPersistentSymbolsSet().contains(symbol.symbol)
    }

    fun addSymbol(symbol: Symbol) {
        symbol.takeIf { !isTracked(symbol) }?.let {
            val symbolsSet = getPersistentSymbolsSet()
            symbolsSet.add(symbol.symbol)
            sharedPreferences.edit().putStringSet(Utility.PERSISTENT_SYMBOLS_SET, symbolsSet).apply()
            listener.onSymbolAdded(symbol)
        }
    }

    fun removeSymbol(symbol: Symbol) {
        symbol.takeIf { isTracked(symbol) }?.let {
            val symbolsSet = getPersistentSymbolsSet()
            symbolsSet.remove(symbol.symbol)
            sharedPreferences.edit().putStringSet(Utility.PERSISTENT_SYMBOLS_SET, symbolsSet).apply()
            listener.onSymbolRemoved(symbol)
        }
    }
}