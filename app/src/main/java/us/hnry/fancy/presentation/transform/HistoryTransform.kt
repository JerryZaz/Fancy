package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.presentation.model.History
import us.hnry.fancy.presentation.model.StockDetail

/**
 * @author Henry
 * 10/24/2017
 */
class HistoryTransform : Function<StockDetail, History> {
    override fun apply(t: StockDetail): History {
        val history = History()
        history.dayHigh = t.dayHigh
        history.dayLow = t.dayLow
        history.yearHigh = t.yearHigh
        history.yearLow = t.yearLow
        return history
    }
}