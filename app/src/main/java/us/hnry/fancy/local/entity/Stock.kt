package us.hnry.fancy.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author Henry
 * 10/25/2017
 */
@Entity(tableName = "table_search_history")
class Stock(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "stocks_id") val uid: Int,
        @ColumnInfo(name = "stocks_symbol") var symbol: String,
        @ColumnInfo(name = "stocks_name") var name: String)