package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.presentation.wrapper.BaseQuoteRowItem
import us.hnry.fancy.presentation.wrapper.HistoryRowItem
import us.hnry.fancy.presentation.wrapper.MovingAverageRowItem
import us.hnry.fancy.presentation.wrapper.SummaryRowItem

/**
 * @author Henry
 * 10/21/2017
 */
class QuoteDetailRowItemTransform : Function<StockDetail, List<BaseQuoteRowItem<*>>> {
    override fun apply(t: StockDetail): List<BaseQuoteRowItem<*>> {
        val list = mutableListOf<BaseQuoteRowItem<*>>()
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        list.add(HistoryRowItem(HistoryTransform().apply(t)))
        list.add(MovingAverageRowItem(MovingAverageTransform().apply(t)))
        return list
    }

}