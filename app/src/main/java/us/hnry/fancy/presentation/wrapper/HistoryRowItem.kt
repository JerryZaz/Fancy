package us.hnry.fancy.presentation.wrapper

import us.hnry.fancy.adapters.util.QuoteAdapterRowTypes
import us.hnry.fancy.presentation.model.History

/**
 * @author Henry
 * 10/24/2017
 */
class HistoryRowItem(history: History) : BaseQuoteRowItem<History>(history) {
    init {
        rowType = QuoteAdapterRowTypes.HISTORY
    }

    fun getDayHigh(): String {
        return item.dayHigh ?: defaultValue
    }

    fun getDayLow(): String {
        return item.dayLow ?: defaultValue
    }

    fun getYearHigh(): String {
        return item.yearHigh ?: defaultValue
    }

    fun getYearLow(): String {
        return item.yearLow ?: defaultValue
    }
}