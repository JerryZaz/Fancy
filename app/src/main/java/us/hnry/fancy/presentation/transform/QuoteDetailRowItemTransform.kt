package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.presentation.wrapper.SummaryRowItem

/**
 * @author Henry
 * 10/21/2017
 */
class QuoteDetailRowItemTransform : Function<StockDetail, List<SummaryRowItem>> {
    override fun apply(t: StockDetail): List<SummaryRowItem> {
        val list = mutableListOf<SummaryRowItem>()
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        list.add(SummaryRowItem(SummaryTransform().apply(t)))
        return list
    }

}