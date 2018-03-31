package us.hnry.fancy.domain

import io.reactivex.Completable
import io.reactivex.Single
import us.hnry.fancy.domain.interactor.StockDetailUseCase
import us.hnry.fancy.domain.interactor.UseCaseHistoryAddSymbol
import us.hnry.fancy.network.model.Quote
import us.hnry.fancy.network.model.SingleQuote

/**
 * @author Henry
 * 10/29/2017
 */
interface StockDetailRepository {
    fun getStockDetails(params: StockDetailUseCase.Params): Single<Quote<SingleQuote>>
    fun addSymbolToSearchHistory(params: UseCaseHistoryAddSymbol.Params): Completable
    fun getSymbolStatus(symbol: String): Single<Boolean>
    fun flipSymbolStatus(symbol: String): Single<Boolean>
    fun addSymbolToFavorites(symbol: String): Single<Boolean>
    fun removeSymbolFromFavorites(symbol: String): Single<Boolean>
}