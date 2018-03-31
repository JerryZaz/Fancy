package us.hnry.fancy.domain

import io.reactivex.Single

/**
 * @author Henry
 * 10/29/2017
 */
interface TrackedSymbolsDataSource {
    fun getPersistentSymbols(): Single<Array<String>>
    fun isTracked(symbol: String): Single<Boolean>
    fun addSymbol(symbol: String): Single<Boolean>
    fun removeSymbol(symbol: String): Single<Boolean>
}