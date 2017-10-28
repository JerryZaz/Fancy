package us.hnry.fancy.data

import io.reactivex.Observable
import io.reactivex.functions.Function
import us.hnry.fancy.domain.HistoryEntryTransform
import us.hnry.fancy.domain.SearchHistoryRepository
import us.hnry.fancy.domain.model.HistoryEntry
import us.hnry.fancy.local.dao.SearchHistoryDao
import us.hnry.fancy.local.entity.Stock
import javax.inject.Inject

/**
 * @author Henry
 * 10/27/2017
 */
class SearchHistoryRepositoryImpl @Inject constructor(private val dao: SearchHistoryDao) : SearchHistoryRepository {
    override fun getSearchHistory(): Observable<List<HistoryEntry>> {
        return dao.getAll().toObservable().flatMap({ it ->
            Observable.just(Transform().apply(it))
        })
    }

    class Transform : Function<List<Stock>, List<HistoryEntry>> {
        override fun apply(t: List<Stock>): List<HistoryEntry> {
            val asList = mutableListOf<HistoryEntry>()
            t.forEach { asList.add(HistoryEntryTransform().apply(it)) }
            return asList
        }
    }
}