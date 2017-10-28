package us.hnry.fancy.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single
import us.hnry.fancy.local.entity.Stock

/**
 * @author Henry
 * 10/26/2017
 */
@Dao
interface SearchHistoryDao : BaseDao<Stock> {
    @Query("SELECT * FROM table_search_history")
    fun getAll(): Single<List<Stock>>
}