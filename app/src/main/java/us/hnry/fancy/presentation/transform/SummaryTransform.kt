package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.presentation.model.Summary

/**
 * @author Henry
 * 10/21/2017
 */
class SummaryTransform : Function<StockDetail, Summary> {
    override fun apply(t: StockDetail): Summary {
        val summary = Summary()
        summary.previousClose = t.previousClose
        summary.open = t.open
        summary.currentAsk = t.currentAsk
        summary.change = t.change
        return summary
    }
}