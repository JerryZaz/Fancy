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
    private val persistentStoreKey by lazy { "savedData" }
    private val sharedPreferences by lazy { context.getSharedPreferences(persistentStoreKey, Context.MODE_PRIVATE) }
    private val defaultSymbols by lazy {
        arrayOf("GOOG", "MSFT", "AAPL", "AMZN", "FB", "TSLA", "T", "TMUS", "YHOO", "NFLX")
    }
    private val persistentSymbolsSetKey by lazy { "key.symbols.set" }

    private fun getPersistentSymbolsSet(): MutableSet<String> {
        return HashSet(sharedPreferences.getStringSet(persistentSymbolsSetKey,
                HashSet(Arrays.asList(*defaultSymbols))))
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
            sharedPreferences.edit().putStringSet(persistentSymbolsSetKey, symbolsSet).apply()
            listener.onSymbolAdded(symbol)
        }
    }

    fun removeSymbol(symbol: Symbol) {
        symbol.takeIf { isTracked(symbol) }?.let {
            val symbolsSet = getPersistentSymbolsSet()
            symbolsSet.remove(symbol.symbol)
            sharedPreferences.edit().putStringSet(persistentSymbolsSetKey, symbolsSet).apply()
            listener.onSymbolRemoved(symbol)
        }
    }
}