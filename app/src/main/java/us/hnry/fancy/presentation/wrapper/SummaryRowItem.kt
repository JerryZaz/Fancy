package us.hnry.fancy.presentation.wrapper

import us.hnry.fancy.adapters.util.QuoteAdapterRowTypes
import us.hnry.fancy.presentation.model.Summary

/**
 * @author Henry
 * 10/21/2017
 */
class SummaryRowItem(summary: Summary) : BaseQuoteRowItem<Summary>(summary) {
    init {
        rowType = QuoteAdapterRowTypes.SUMMARY
    }

    fun getPreviousClose(): String {
        return item.previousClose ?: defaultValue
    }

    fun getOpen(): String {
        return item.open ?: defaultValue
    }

    fun getCurrentAsk(): String {
        return item.currentAsk ?: defaultValue
    }

    fun getChange(): String {
        val change = item.change ?: defaultEmpty
        val percentChange = item.percentChange ?: defaultEmpty
        return "$change ($percentChange)"
    }
}