package us.hnry.fancy.domain.interactor

import io.reactivex.Completable
import io.reactivex.Scheduler
import us.hnry.fancy.domain.StockDetailRepository
import us.hnry.fancy.local.entity.Stock

/**
 * @author Henry
 * 10/29/2017
 */
class UseCaseHistoryAddSymbol(private val repository: StockDetailRepository, private val subscribeOn: Scheduler, private val observeOn: Scheduler) {
    fun execute(params: Params): Completable {
        return repository.addSymbolToSearchHistory(params).subscribeOn(subscribeOn).observeOn(observeOn)
    }

    class Params(symbol: String, nameTag: String) {
        val entry = Stock(0, symbol, nameTag)
    }
}