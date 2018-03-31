package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.presentation.wrapper.StockDetailRowItem
import us.hnry.fancy.utils.SingleQuoteMapTransform

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailRowItemsTransform : Function<SingleQuote, List<StockDetailRowItem>> {
    override fun apply(t: SingleQuote): List<StockDetailRowItem> {
        val entries = mutableListOf<StockDetailRowItem>()
        val details = SingleQuoteMapTransform().apply(t)

        details.forEach({ entry -> entries.add(StockDetailRowItem(entry.key, entry.value)) })
        return entries
    }
}