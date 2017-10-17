package us.hnry.fancy.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author Henry
 * 10/6/2017
 */

data class SingleQuote(
        @SerializedName("symbol") val symbol: String?,
        @SerializedName("Ask") val ask: String?,
        @SerializedName("AverageDailyVolume") val averageDailyVolume: String?,
        @SerializedName("Bid") val bid: String?,
        @SerializedName("AskRealtime") val askRealTime: String?,
        @SerializedName("BidRealtime") val bidRealTime: String?,
        @SerializedName("BookValue") val bookValue: String?,
        @SerializedName("Change_PercentChange") val changePercentChange: String?,
        @SerializedName("Change") val change: String?,
        @SerializedName("Commission") val commission: String?,
        @SerializedName("Currency") val currency: String?,
        @SerializedName("ChangeRealtime") val changeRealTime: String?,
        @SerializedName("AfterHoursChangeRealtime") val afterHoursChangeRealTime: String?,
        @SerializedName("DividendShare") val dividendShare: String?,
        @SerializedName("LastTradeDate") val lastTradeDate: String?,
        @SerializedName("TradeDate") val tradeDate: String?,
        @SerializedName("EarningsShare") val earningsShare: String?,
        @SerializedName("ErrorIndicationreturnedforsymbolchangedinvalid") val errorIndicationReturnedForSymbolChangedInvalid: String?,
        @SerializedName("EPSEstimateCurrentYear") val epsEstimateCurrentYear: String?,
        @SerializedName("EPSEstimateNextYear") val epsEstimateNextYear: String?,
        @SerializedName("EPSEstimateNextQuarter") val epsEstimateNextQuarter: String?,
        @SerializedName("DaysLow") val daysLow: String?,
        @SerializedName("DaysHigh") val daysHigh: String?,
        @SerializedName("YearLow") val yearLow: String?,
        @SerializedName("YearHigh") val yearHigh: String?,
        @SerializedName("HoldingsGainPercent") val holdingsGainPercent: String?,
        @SerializedName("AnnualizedGain") val annualizedGain: String?,
        @SerializedName("HoldingsGain") val holdingsGain: String?,
        @SerializedName("HoldingsGainPercentRealtime") val holdingsGainPercentRealtime: String?,
        @SerializedName("HoldingsGainRealtime") val holdingsGainRealtime: String?,
        @SerializedName("MoreInfo") val moreInfo: String?,
        @SerializedName("OrderBookRealtime") val orderBookRealtime: String?,
        @SerializedName("MarketCapitalization") val marketCapitalization: String?,
        @SerializedName("MarketCapRealtime") val marketCapRealtime: String?,
        @SerializedName("EBITDA") val earningsBeforeInterestTaxDepreciationAmortization: String?,
        @SerializedName("ChangeFromYearLow") val changeFromYearLow: String?,
        @SerializedName("PercentChangeFromYearLow") val percentChangeFromYearLow: String?,
        @SerializedName("LastTradeRealtimeWithTime") val lastTradeRealtimeWithTime: String?,
        @SerializedName("ChangePercentRealtime") val changePercentRealtime: String?,
        @SerializedName("ChangeFromYearHigh") val changeFromYearHigh: String?,
        @SerializedName("PercebtChangeFromYearHigh") val percentChangeFromYearHigh: String?,
        @SerializedName("LastTradeWithTime") val lastTradeWithTime: String?,
        @SerializedName("LastTradePriceOnly") val lastTradePriceOnly: String?,
        @SerializedName("HighLimit") val highLimit: String?,
        @SerializedName("LowLimit") val lowLimit: String?,
        @SerializedName("DaysRange") val daysRange: String?,
        @SerializedName("DaysRangeRealtime") val daysRangeRealtime: String?,
        @SerializedName("FiftydayMovingAverage") val fiftyDaysMovingAverage: String?,
        @SerializedName("TwoHundreddayMovingAverage") val twoHundredDaysMovingAverage: String?,
        @SerializedName("ChangeFromTwoHundreddayMovingAverage") val changeFromTwoHundredDaysMovingAverage: String?,
        @SerializedName("PercentChangeFromTwoHundreddayMovingAverage") val percentChangeFromTwoHundredDaysMovingAverage: String?,
        @SerializedName("ChangeFromFiftydayMovingAverage") val changeFromFiftyDaysMovingAverage: String?,
        @SerializedName("PercentChangeFromFiftydayMovingAverage") val percentChangeFromFiftyDaysMovingAverage: String?,
        @SerializedName("Name") val name: String?,
        @SerializedName("Notes") val notes: String?,
        @SerializedName("Open") val open: String?,
        @SerializedName("PreviousClose") val previousClose: String?,
        @SerializedName("PricePaid") val pricePaid: String?,
        @SerializedName("ChangeinPercent") val changePercent: String?,
        @SerializedName("PriceSales") val priceSales: String?,
        @SerializedName("PriceBook") val priceBook: String?,
        @SerializedName("ExDividendDate") val exDividendDate: String?,
        @SerializedName("PERatio") val peRatio: String?,
        @SerializedName("DividendPayDate") val dividendPayDate: String?,
        @SerializedName("PERatioRealtime") val peRatioRealtime: String?,
        @SerializedName("PEGRatio") val pegRatio: String?,
        @SerializedName("PriceEPSEstimateCurrentYear") val priceEPSEstimateCurrentYear: String?,
        @SerializedName("PriceEPSEstimateNextYear") val priceEPSEstimateNextYear: String?,
        @SerializedName("SharesOwned") val sharesOwned: String?,
        @SerializedName("ShortRatio") val shortRatio: String?,
        @SerializedName("LastTradeTime") val lastTradeTime: String?,
        @SerializedName("TickerTrend") val tickerTrend: String?,
        @SerializedName("OneyrTargetPrice") val oneYearTargetPrice: String?,
        @SerializedName("Volume") val volume: String?,
        @SerializedName("HoldingsValue") val holdingsValue: String?,
        @SerializedName("HoldingsValueRealtime") val holdingsValueRealtime: String?,
        @SerializedName("YearRange") val yearRange: String?,
        @SerializedName("DaysValueChange") val daysValueChange: String?,
        @SerializedName("DaysValueChangeRealtime") val daysValueChangeRealtime: String?,
        @SerializedName("StockExchange") val stockExchange: String?,
        @SerializedName("DividendYield") val dividendYield: String?,
        @SerializedName("PercentChange") val percentChange: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(symbol)
        writeString(ask)
        writeString(averageDailyVolume)
        writeString(bid)
        writeString(askRealTime)
        writeString(bidRealTime)
        writeString(bookValue)
        writeString(changePercentChange)
        writeString(change)
        writeString(commission)
        writeString(currency)
        writeString(changeRealTime)
        writeString(afterHoursChangeRealTime)
        writeString(dividendShare)
        writeString(lastTradeDate)
        writeString(tradeDate)
        writeString(earningsShare)
        writeString(errorIndicationReturnedForSymbolChangedInvalid)
        writeString(epsEstimateCurrentYear)
        writeString(epsEstimateNextYear)
        writeString(epsEstimateNextQuarter)
        writeString(daysLow)
        writeString(daysHigh)
        writeString(yearLow)
        writeString(yearHigh)
        writeString(holdingsGainPercent)
        writeString(annualizedGain)
        writeString(holdingsGain)
        writeString(holdingsGainPercentRealtime)
        writeString(holdingsGainRealtime)
        writeString(moreInfo)
        writeString(orderBookRealtime)
        writeString(marketCapitalization)
        writeString(marketCapRealtime)
        writeString(earningsBeforeInterestTaxDepreciationAmortization)
        writeString(changeFromYearLow)
        writeString(percentChangeFromYearLow)
        writeString(lastTradeRealtimeWithTime)
        writeString(changePercentRealtime)
        writeString(changeFromYearHigh)
        writeString(percentChangeFromYearHigh)
        writeString(lastTradeWithTime)
        writeString(lastTradePriceOnly)
        writeString(highLimit)
        writeString(lowLimit)
        writeString(daysRange)
        writeString(daysRangeRealtime)
        writeString(fiftyDaysMovingAverage)
        writeString(twoHundredDaysMovingAverage)
        writeString(changeFromTwoHundredDaysMovingAverage)
        writeString(percentChangeFromTwoHundredDaysMovingAverage)
        writeString(changeFromFiftyDaysMovingAverage)
        writeString(percentChangeFromFiftyDaysMovingAverage)
        writeString(name)
        writeString(notes)
        writeString(open)
        writeString(previousClose)
        writeString(pricePaid)
        writeString(changePercent)
        writeString(priceSales)
        writeString(priceBook)
        writeString(exDividendDate)
        writeString(peRatio)
        writeString(dividendPayDate)
        writeString(peRatioRealtime)
        writeString(pegRatio)
        writeString(priceEPSEstimateCurrentYear)
        writeString(priceEPSEstimateNextYear)
        writeString(sharesOwned)
        writeString(shortRatio)
        writeString(lastTradeTime)
        writeString(tickerTrend)
        writeString(oneYearTargetPrice)
        writeString(volume)
        writeString(holdingsValue)
        writeString(holdingsValueRealtime)
        writeString(yearRange)
        writeString(daysValueChange)
        writeString(daysValueChangeRealtime)
        writeString(stockExchange)
        writeString(dividendYield)
        writeString(percentChange)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SingleQuote> = object : Parcelable.Creator<SingleQuote> {
            override fun createFromParcel(source: Parcel): SingleQuote = SingleQuote(source)
            override fun newArray(size: Int): Array<SingleQuote?> = arrayOfNulls(size)
        }
    }
}