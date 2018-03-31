package us.hnry.fancy.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import us.hnry.fancy.domain.StockDetailRepository
import us.hnry.fancy.network.model.Quote
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.utils.QuoteQueryBuilder

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailUseCase(private val repository: StockDetailRepository, subscribeOn: Scheduler, observeOn: Scheduler) : BaseUseCase<StockDetailUseCase.Params, Quote<SingleQuote>>(subscribeOn, observeOn) {
    override fun execute(params: Params): Observable<Quote<SingleQuote>> {
        return repository.getStockDetails(params).subscribeOn(subscribeOn).observeOn(observeOn).toObservable()
    }

    class Params(val symbol: String) {
        val builtQuery = QuoteQueryBuilder(symbol).build()
    }
}