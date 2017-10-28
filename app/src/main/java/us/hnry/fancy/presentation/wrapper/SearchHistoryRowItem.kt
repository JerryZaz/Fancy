package us.hnry.fancy.presentation.wrapper

import android.databinding.BaseObservable
import us.hnry.fancy.domain.model.HistoryEntry
import java.util.*

/**
 * @author Henry
 * 10/28/2017
 */
class SearchHistoryRowItem(val item: HistoryEntry) : BaseObservable() {
    fun getSymbol(): String {
        return item.symbol.toUpperCase(Locale.US)
    }

    fun getName(): String {
        return item.name
    }
}