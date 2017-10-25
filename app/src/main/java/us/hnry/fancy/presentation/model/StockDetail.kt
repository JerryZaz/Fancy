package us.hnry.fancy.presentation.model

/**
 * @author Henry
 * 10/21/2017
 */
class StockDetail {

    // summary
    var symbol: String? = null
    var symbolName: String? = null
    var previousClose: String? = null
    var open: String? = null
    var currentAsk: String? = null
    var change: String? = null
    var percentChange: String? = null

    // history
    var dayHigh: String? = null
    var dayLow: String? = null
    var yearHigh: String? = null
    var yearLow: String? = null

    // EMA
    var fiftyDayEma: String? = null
    var twoHundredDayEma: String? = null
}