package us.hnry.fancy.presentation.wrapper

import android.databinding.BaseObservable

/**
 * @author Henry
 * 10/21/2017
 */
open class BaseQuoteRowItem<out T>(val item: T) : BaseObservable() {
    var rowType = -1
    internal val defaultValue = "N/A"
    internal val defaultEmpty = "--"
}