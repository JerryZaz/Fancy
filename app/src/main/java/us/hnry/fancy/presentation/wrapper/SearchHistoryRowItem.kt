package us.hnry.fancy.presentation.wrapper

import android.databinding.BaseObservable
import us.hnry.fancy.domain.model.HistoryEntry
import java.util.*

/**
 * @author Henry
 * 10/28/2017
 */
class SearchHistoryRowItem(val item: HistoryEntry, private val handler: EventHandler) : BaseObservable() {
    fun getSymbol(): String {
        return item.symbol.toUpperCase(Locale.US)
    }

    fun getName(): String {
        return item.name
    }

    fun handleClick() {
        handler.historyRowItemClicked(getSymbol())
    }

    interface EventHandler {
        fun historyRowItemClicked(symbol: String)
    }
}