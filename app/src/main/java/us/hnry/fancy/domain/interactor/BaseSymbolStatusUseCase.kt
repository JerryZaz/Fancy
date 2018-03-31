package us.hnry.fancy.domain.interactor

import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * @author Henry
 * 10/29/2017
 */
abstract class BaseSymbolStatusUseCase(val subscribeOn: Scheduler, val observeOn: Scheduler) {
    abstract fun execute(symbol: String): Single<Boolean>
}