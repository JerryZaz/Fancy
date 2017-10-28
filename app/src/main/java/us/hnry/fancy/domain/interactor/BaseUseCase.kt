package us.hnry.fancy.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * @author Henry
 * 10/27/2017
 */
abstract class BaseUseCase<S, R>(val subscribeOn: Scheduler, val observeOn: Scheduler) {
    abstract fun execute(params: S): Observable<R>
}