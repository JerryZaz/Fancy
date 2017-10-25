package us.hnry.fancy.presentation.transform

import io.reactivex.functions.Function
import us.hnry.fancy.presentation.model.MovingAverage
import us.hnry.fancy.presentation.model.StockDetail

/**
 * @author Henry
 * 10/25/2017
 */
class MovingAverageTransform : Function<StockDetail, MovingAverage> {
    override fun apply(t: StockDetail): MovingAverage {
        val data = MovingAverage()
        data.fiftyDays = t.fiftyDayEma
        data.twoHundredDays = t.twoHundredDayEma
        return data
    }
}