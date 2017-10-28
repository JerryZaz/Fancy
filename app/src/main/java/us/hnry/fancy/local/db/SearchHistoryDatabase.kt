package us.hnry.fancy.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import us.hnry.fancy.local.dao.SearchHistoryDao
import us.hnry.fancy.local.entity.Stock

/**
 * @author Henry
 * 10/27/2017
 */
@Database(version = 1, entities = arrayOf(Stock::class), exportSchema = false)
abstract class SearchHistoryDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}