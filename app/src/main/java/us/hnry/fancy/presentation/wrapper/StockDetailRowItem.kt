package us.hnry.fancy.presentation.wrapper

import android.databinding.BaseObservable

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailRowItem(private val key: String, private val value: String) : BaseObservable() {
    fun getFormattedKey(): String {
        return key.capitalize()
    }

    fun getFormattedValue(): String {
        return value
    }
}