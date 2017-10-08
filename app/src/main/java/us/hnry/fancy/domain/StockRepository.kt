package us.hnry.fancy.domain

import io.reactivex.Observable
import us.hnry.fancy.network.model.SingleQuote

/**
 * @author Henry
 * 10/7/2017
 */
interface StockRepository {
    fun getQuotes(params: StockUseCase.Params): Observable<List<SingleQuote>>
}