package us.hnry.fancy.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.utils.QuoteQueryBuilder
import java.util.concurrent.TimeUnit

/**
 * @author Henry
 * 10/7/2017
 */
class StockUseCase(private val stockRepository: StockRepository, subscribeOn: Scheduler, observeOn: Scheduler) : BaseUseCase<StockUseCase.Params, List<SingleQuote>>(subscribeOn, observeOn) {
    override fun execute(params: Params): Observable<List<SingleQuote>> {
        return Observable.interval(0, 15, TimeUnit.SECONDS, subscribeOn)
                .flatMap { stockRepository.getQuotes(params) }
                .subscribeOn(subscribeOn).observeOn(observeOn).retry()
    }

    class Params(vararg symbols: String) {
        val builtQuery: String = QuoteQueryBuilder(*symbols).build()
        val symbolsCount: Int = symbols.count()
    }
}