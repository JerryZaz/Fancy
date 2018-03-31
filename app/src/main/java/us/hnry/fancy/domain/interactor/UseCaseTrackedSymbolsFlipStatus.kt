package us.hnry.fancy.domain.interactor

import io.reactivex.Scheduler
import io.reactivex.Single
import us.hnry.fancy.domain.StockDetailRepository

/**
 * @author Henry
 * 10/29/2017
 */
class UseCaseTrackedSymbolsFlipStatus(private val repository: StockDetailRepository, subscribeOn: Scheduler, observeOn: Scheduler) : BaseSymbolStatusUseCase(subscribeOn, observeOn) {
    override fun execute(symbol: String): Single<Boolean> {
        return repository.flipSymbolStatus(symbol)
    }
}