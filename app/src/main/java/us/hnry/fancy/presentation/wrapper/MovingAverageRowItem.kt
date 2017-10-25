package us.hnry.fancy.presentation.wrapper

import us.hnry.fancy.adapters.util.QuoteAdapterRowTypes
import us.hnry.fancy.presentation.model.MovingAverage

/**
 * @author Henry
 * 10/25/2017
 */
class MovingAverageRowItem(ema: MovingAverage) : BaseQuoteRowItem<MovingAverage>(ema) {
    init {
        rowType = QuoteAdapterRowTypes.EMA
    }

    fun getFiftyDaysEma(): String {
        return item.fiftyDays ?: defaultValue
    }

    fun getTwoHundredDaysEma(): String {
        return item.twoHundredDays ?: defaultValue
    }
}