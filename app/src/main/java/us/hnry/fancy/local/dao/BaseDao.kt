package us.hnry.fancy.local.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

/**
 * @author Henry
 * 10/25/2017
 */
interface BaseDao<in T> {

    @Insert
    fun insert(item: T)

    @Insert
    fun insert(vararg item: T)

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)
}