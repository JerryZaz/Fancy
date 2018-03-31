package us.hnry.fancy.data

import android.content.SharedPreferences
import io.reactivex.Single
import us.hnry.fancy.domain.TrackedSymbolsDataSource
import java.util.*

class TrackedSymbolsDataSourceImpl(private val sharedPreferences: SharedPreferences) : TrackedSymbolsDataSource {

    private val persistentSymbolsSetKey = "key.symbols.set"
    private val defaultSymbols by lazy {
        arrayOf("GOOG", "MSFT", "AAPL", "AMZN", "FB", "TSLA", "T", "TMUS", "YHOO", "NFLX")
    }


    override fun getPersistentSymbols(): Single<Array<String>> {
        return Single.just(getPersistentSymbolsSet().toTypedArray())
    }

    override fun isTracked(symbol: String): Single<Boolean> {
        return Single.just(getPersistentSymbolsSet().contains(symbol))
    }

    override fun addSymbol(symbol: String): Single<Boolean> {

        return if (getPersistentSymbolsSet().contains(symbol)) {
            Single.error(Throwable("Symbol already tracked"))
        } else {
            val symbolsSet = getPersistentSymbolsSet()
            symbolsSet.add(symbol)
            sharedPreferences.edit().putStringSet(persistentSymbolsSetKey, symbolsSet).apply()
            Single.just(true)
        }
    }

    override fun removeSymbol(symbol: String): Single<Boolean> {

        return if (getPersistentSymbolsSet().contains(symbol)) {
            val symbolsSet = getPersistentSymbolsSet()
            symbolsSet.remove(symbol)
            sharedPreferences.edit().putStringSet(persistentSymbolsSetKey, symbolsSet).apply()
            Single.just(false)
        } else {
            return Single.error(Throwable("Symbol not tracked"))
        }
    }

    private fun getPersistentSymbolsSet(): MutableSet<String> {
        return HashSet(sharedPreferences.getStringSet(persistentSymbolsSetKey,
                HashSet(Arrays.asList(*defaultSymbols))))
    }
}