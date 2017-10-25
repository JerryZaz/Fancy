package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.presentation.model.StockDetail

/**
 * @author Henry
 * 10/21/2017
 */
class QuoteTransform : Function<SingleQuote, StockDetail> {
    override fun apply(t: SingleQuote): StockDetail {
        val quote = StockDetail()
        quote.symbol = t.symbol
        quote.symbolName = t.name
        quote.previousClose = t.previousClose
        quote.open = t.open
        quote.currentAsk = t.ask
        quote.change = t.change
        quote.percentChange = t.percentChange

        // history
        quote.dayHigh = t.daysHigh
        quote.dayLow = t.daysLow
        quote.yearHigh = t.yearHigh
        quote.yearLow = t.yearLow
        return quote
    }
}