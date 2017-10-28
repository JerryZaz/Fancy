package us.hnry.fancy.domain

import io.reactivex.Observable
import us.hnry.fancy.domain.model.HistoryEntry

/**
 * @author Henry
 * 10/27/2017
 */
interface SearchHistoryRepository {
    fun getSearchHistory(): Observable<List<HistoryEntry>>
}