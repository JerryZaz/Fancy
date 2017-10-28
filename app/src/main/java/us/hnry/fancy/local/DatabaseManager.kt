package us.hnry.fancy.local

import android.arch.persistence.room.Room
import android.content.Context
import android.content.ContextWrapper
import us.hnry.fancy.local.db.SearchHistoryDatabase

/**
 * @author Henry
 * 10/27/2017
 */
class DatabaseManager(context: Context) : ContextWrapper(context) {
    val searchHistoryDatabase by lazy { Room.databaseBuilder(context, SearchHistoryDatabase::class.java, "search_history_db").fallbackToDestructiveMigration().build() }
}