package us.hnry.fancy.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import us.hnry.fancy.domain.SearchHistoryRepository
import us.hnry.fancy.domain.model.HistoryEntry

/**
 * @author Henry
 * 10/27/2017
 */
class SearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository, subscribeOn: Scheduler, observeOn: Scheduler) : BaseUseCase<Unit?, List<HistoryEntry>>(subscribeOn, observeOn) {
    override fun execute(params: Unit?): Observable<List<HistoryEntry>> {
        return searchHistoryRepository.getSearchHistory().subscribeOn(subscribeOn).observeOn(observeOn)
    }
}