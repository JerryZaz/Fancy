package us.hnry.fancy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Henry
 *         10/6/2017
 */

public class SingleQuote implements Parcelable {
    public static final Creator<SingleQuote> CREATOR = new Creator<SingleQuote>() {
        @Override
        public SingleQuote createFromParcel(Parcel in) {
            return new SingleQuote(in);
        }

        @Override
        public SingleQuote[] newArray(int size) {
            return new SingleQuote[size];
        }
    };
    private String symbol;
    private String Ask;
    private String AverageDailyVolume;
    private String Bid;
    private String AskRealtime;
    private String BidRealtime;
    private String BookValue;
    private String ChangePercentChange;
    private String Change;
    private String Commission;
    private String Currency;
    private String ChangeRealtime;
    private String AfterHoursChangeRealtime;
    private String DividendShare;
    private String LastTradeDate;
    private String TradeDate;
    private String EarningsShare;
    private String ErrorIndicationreturnedforsymbolchangedinvalid;
    private String EPSEstimateCurrentYear;
    private String EPSEstimateNextYear;
    private String EPSEstimateNextQuarter;
    private String DaysLow;
    private String DaysHigh;
    private String YearLow;
    private String YearHigh;
    private String HoldingsGainPercent;
    private String AnnualizedGain;
    private String HoldingsGain;
    private String HoldingsGainPercentRealtime;
    private String HoldingsGainRealtime;
    private String MoreInfo;
    private String OrderBookRealtime;
    private String MarketCapitalization;
    private String MarketCapRealtime;
    private String EBITDA;
    private String ChangeFromYearLow;
    private String PercentChangeFromYearLow;
    private String LastTradeRealtimeWithTime;
    private String ChangePercentRealtime;
    private String ChangeFromYearHigh;

    @SerializedName("PercebtChangeFromYearHigh")
    private String PercentChangeFromYearHigh;

    private String LastTradeWithTime;
    private String LastTradePriceOnly;
    private String HighLimit;
    private String LowLimit;
    private String DaysRange;
    private String DaysRangeRealtime;

    @SerializedName("FiftydayMovingAverage")
    private String FiftyDaysMovingAverage;

    @SerializedName("TwoHundreddayMovingAverage")
    private String TwoHundredDaysMovingAverage;

    @SerializedName("ChangeFromTwoHundreddayMovingAverage")
    private String ChangeFromTwoHundredDaysMovingAverage;

    @SerializedName("PercentChangeFromTwoHundreddayMovingAverage")
    private String PercentChangeFromTwoHundredDaysMovingAverage;

    @SerializedName("ChangeFromFiftydayMovingAverage")
    private String ChangeFromFiftyDaysMovingAverage;

    @SerializedName("PercentChangeFromFiftydayMovingAverage")
    private String PercentChangeFromFiftyDaysMovingAverage;

    private String Name;
    private String Notes;
    private String Open;
    private String PreviousClose;
    private String PricePaid;

    @SerializedName("ChangeinPercent")
    private String ChangePercent;

    private String PriceSales;
    private String PriceBook;
    private String ExDividendDate;
    private String PERatio;
    private String DividendPayDate;
    private String PERatioRealtime;
    private String PEGRatio;
    private String PriceEPSEstimateCurrentYear;
    private String PriceEPSEstimateNextYear;
    private String SharesOwned;
    private String ShortRatio;
    private String LastTradeTime;
    private String TickerTrend;

    @SerializedName("OneyrTargetPrice")
    private String OneYearTargetPrice;

    private String Volume;
    private String HoldingsValue;
    private String HoldingsValueRealtime;
    private String YearRange;
    private String DaysValueChange;
    private String DaysValueChangeRealtime;
    private String StockExchange;
    private String DividendYield;
    private String PercentChange;

    public SingleQuote(String symbol, String ask, String averageDailyVolume, String bid, String askRealtime, String bidRealtime, String bookValue, String changePercentChange, String change, String commission, String currency, String changeRealtime, String afterHoursChangeRealtime, String dividendShare, String lastTradeDate, String tradeDate, String earningsShare, String errorIndicationreturnedforsymbolchangedinvalid, String EPSEstimateCurrentYear, String EPSEstimateNextYear, String EPSEstimateNextQuarter, String daysLow, String daysHigh, String yearLow, String yearHigh, String holdingsGainPercent, String annualizedGain, String holdingsGain, String holdingsGainPercentRealtime, String holdingsGainRealtime, String moreInfo, String orderBookRealtime, String marketCapitalization, String marketCapRealtime, String EBITDA, String changeFromYearLow, String percentChangeFromYearLow, String lastTradeRealtimeWithTime, String changePercentRealtime, String changeFromYearHigh, String percentChangeFromYearHigh, String lastTradeWithTime, String lastTradePriceOnly, String highLimit, String lowLimit, String daysRange, String daysRangeRealtime, String fiftyDaysMovingAverage, String twoHundredDaysMovingAverage, String changeFromTwoHundredDaysMovingAverage, String percentChangeFromTwoHundredDaysMovingAverage, String changeFromFiftyDaysMovingAverage, String percentChangeFromFiftyDaysMovingAverage, String name, String notes, String open, String previousClose, String pricePaid, String changePercent, String priceSales, String priceBook, String exDividendDate, String PERatio, String dividendPayDate, String PERatioRealtime, String PEGRatio, String priceEPSEstimateCurrentYear, String priceEPSEstimateNextYear, String sharesOwned, String shortRatio, String lastTradeTime, String tickerTrend, String oneYearTargetPrice, String volume, String holdingsValue, String holdingsValueRealtime, String yearRange, String daysValueChange, String daysValueChangeRealtime, String stockExchange, String dividendYield, String percentChange) {
        this.symbol = symbol;
        Ask = ask;
        AverageDailyVolume = averageDailyVolume;
        Bid = bid;
        AskRealtime = askRealtime;
        BidRealtime = bidRealtime;
        BookValue = bookValue;
        ChangePercentChange = changePercentChange;
        Change = change;
        Commission = commission;
        Currency = currency;
        ChangeRealtime = changeRealtime;
        AfterHoursChangeRealtime = afterHoursChangeRealtime;
        DividendShare = dividendShare;
        LastTradeDate = lastTradeDate;
        TradeDate = tradeDate;
        EarningsShare = earningsShare;
        ErrorIndicationreturnedforsymbolchangedinvalid = errorIndicationreturnedforsymbolchangedinvalid;
        this.EPSEstimateCurrentYear = EPSEstimateCurrentYear;
        this.EPSEstimateNextYear = EPSEstimateNextYear;
        this.EPSEstimateNextQuarter = EPSEstimateNextQuarter;
        DaysLow = daysLow;
        DaysHigh = daysHigh;
        YearLow = yearLow;
        YearHigh = yearHigh;
        HoldingsGainPercent = holdingsGainPercent;
        AnnualizedGain = annualizedGain;
        HoldingsGain = holdingsGain;
        HoldingsGainPercentRealtime = holdingsGainPercentRealtime;
        HoldingsGainRealtime = holdingsGainRealtime;
        MoreInfo = moreInfo;
        OrderBookRealtime = orderBookRealtime;
        MarketCapitalization = marketCapitalization;
        MarketCapRealtime = marketCapRealtime;
        this.EBITDA = EBITDA;
        ChangeFromYearLow = changeFromYearLow;
        PercentChangeFromYearLow = percentChangeFromYearLow;
        LastTradeRealtimeWithTime = lastTradeRealtimeWithTime;
        ChangePercentRealtime = changePercentRealtime;
        ChangeFromYearHigh = changeFromYearHigh;
        PercentChangeFromYearHigh = percentChangeFromYearHigh;
        LastTradeWithTime = lastTradeWithTime;
        LastTradePriceOnly = lastTradePriceOnly;
        HighLimit = highLimit;
        LowLimit = lowLimit;
        DaysRange = daysRange;
        DaysRangeRealtime = daysRangeRealtime;
        FiftyDaysMovingAverage = fiftyDaysMovingAverage;
        TwoHundredDaysMovingAverage = twoHundredDaysMovingAverage;
        ChangeFromTwoHundredDaysMovingAverage = changeFromTwoHundredDaysMovingAverage;
        PercentChangeFromTwoHundredDaysMovingAverage = percentChangeFromTwoHundredDaysMovingAverage;
        ChangeFromFiftyDaysMovingAverage = changeFromFiftyDaysMovingAverage;
        PercentChangeFromFiftyDaysMovingAverage = percentChangeFromFiftyDaysMovingAverage;
        Name = name;
        Notes = notes;
        Open = open;
        PreviousClose = previousClose;
        PricePaid = pricePaid;
        ChangePercent = changePercent;
        PriceSales = priceSales;
        PriceBook = priceBook;
        ExDividendDate = exDividendDate;
        this.PERatio = PERatio;
        DividendPayDate = dividendPayDate;
        this.PERatioRealtime = PERatioRealtime;
        this.PEGRatio = PEGRatio;
        PriceEPSEstimateCurrentYear = priceEPSEstimateCurrentYear;
        PriceEPSEstimateNextYear = priceEPSEstimateNextYear;
        SharesOwned = sharesOwned;
        ShortRatio = shortRatio;
        LastTradeTime = lastTradeTime;
        TickerTrend = tickerTrend;
        OneYearTargetPrice = oneYearTargetPrice;
        Volume = volume;
        HoldingsValue = holdingsValue;
        HoldingsValueRealtime = holdingsValueRealtime;
        YearRange = yearRange;
        DaysValueChange = daysValueChange;
        DaysValueChangeRealtime = daysValueChangeRealtime;
        StockExchange = stockExchange;
        DividendYield = dividendYield;
        PercentChange = percentChange;
    }

    protected SingleQuote(Parcel in) {
        symbol = in.readString();
        Ask = in.readString();
        AverageDailyVolume = in.readString();
        Bid = in.readString();
        AskRealtime = in.readString();
        BidRealtime = in.readString();
        BookValue = in.readString();
        ChangePercentChange = in.readString();
        Change = in.readString();
        Commission = in.readString();
        Currency = in.readString();
        ChangeRealtime = in.readString();
        AfterHoursChangeRealtime = in.readString();
        DividendShare = in.readString();
        LastTradeDate = in.readString();
        TradeDate = in.readString();
        EarningsShare = in.readString();
        ErrorIndicationreturnedforsymbolchangedinvalid = in.readString();
        EPSEstimateCurrentYear = in.readString();
        EPSEstimateNextYear = in.readString();
        EPSEstimateNextQuarter = in.readString();
        DaysLow = in.readString();
        DaysHigh = in.readString();
        YearLow = in.readString();
        YearHigh = in.readString();
        HoldingsGainPercent = in.readString();
        AnnualizedGain = in.readString();
        HoldingsGain = in.readString();
        HoldingsGainPercentRealtime = in.readString();
        HoldingsGainRealtime = in.readString();
        MoreInfo = in.readString();
        OrderBookRealtime = in.readString();
        MarketCapitalization = in.readString();
        MarketCapRealtime = in.readString();
        EBITDA = in.readString();
        ChangeFromYearLow = in.readString();
        PercentChangeFromYearLow = in.readString();
        LastTradeRealtimeWithTime = in.readString();
        ChangePercentRealtime = in.readString();
        ChangeFromYearHigh = in.readString();
        PercentChangeFromYearHigh = in.readString();
        LastTradeWithTime = in.readString();
        LastTradePriceOnly = in.readString();
        HighLimit = in.readString();
        LowLimit = in.readString();
        DaysRange = in.readString();
        DaysRangeRealtime = in.readString();
        FiftyDaysMovingAverage = in.readString();
        TwoHundredDaysMovingAverage = in.readString();
        ChangeFromTwoHundredDaysMovingAverage = in.readString();
        PercentChangeFromTwoHundredDaysMovingAverage = in.readString();
        ChangeFromFiftyDaysMovingAverage = in.readString();
        PercentChangeFromFiftyDaysMovingAverage = in.readString();
        Name = in.readString();
        Notes = in.readString();
        Open = in.readString();
        PreviousClose = in.readString();
        PricePaid = in.readString();
        ChangePercent = in.readString();
        PriceSales = in.readString();
        PriceBook = in.readString();
        ExDividendDate = in.readString();
        PERatio = in.readString();
        DividendPayDate = in.readString();
        PERatioRealtime = in.readString();
        PEGRatio = in.readString();
        PriceEPSEstimateCurrentYear = in.readString();
        PriceEPSEstimateNextYear = in.readString();
        SharesOwned = in.readString();
        ShortRatio = in.readString();
        LastTradeTime = in.readString();
        TickerTrend = in.readString();
        OneYearTargetPrice = in.readString();
        Volume = in.readString();
        HoldingsValue = in.readString();
        HoldingsValueRealtime = in.readString();
        YearRange = in.readString();
        DaysValueChange = in.readString();
        DaysValueChangeRealtime = in.readString();
        StockExchange = in.readString();
        DividendYield = in.readString();
        PercentChange = in.readString();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAsk() {
        return Ask;
    }

    public String getAverageDailyVolume() {
        return AverageDailyVolume;
    }

    public String getBid() {
        return Bid;
    }

    public String getAskRealtime() {
        return AskRealtime;
    }

    public String getBidRealtime() {
        return BidRealtime;
    }

    public String getBookValue() {
        return BookValue;
    }

    public String getChangePercentChange() {
        return ChangePercentChange;
    }

    public String getChange() {
        return Change;
    }

    public String getCommission() {
        return Commission;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getChangeRealtime() {
        return ChangeRealtime;
    }

    public String getAfterHoursChangeRealtime() {
        return AfterHoursChangeRealtime;
    }

    public String getDividendShare() {
        return DividendShare;
    }

    public String getLastTradeDate() {
        return LastTradeDate;
    }

    public String getTradeDate() {
        return TradeDate;
    }

    public String getEarningsShare() {
        return EarningsShare;
    }

    public String getErrorIndicationreturnedforsymbolchangedinvalid() {
        return ErrorIndicationreturnedforsymbolchangedinvalid;
    }

    public String getEPSEstimateCurrentYear() {
        return EPSEstimateCurrentYear;
    }

    public String getEPSEstimateNextYear() {
        return EPSEstimateNextYear;
    }

    public String getEPSEstimateNextQuarter() {
        return EPSEstimateNextQuarter;
    }

    public String getDaysLow() {
        return DaysLow;
    }

    public String getDaysHigh() {
        return DaysHigh;
    }

    public String getYearLow() {
        return YearLow;
    }

    public String getYearHigh() {
        return YearHigh;
    }

    public String getHoldingsGainPercent() {
        return HoldingsGainPercent;
    }

    public String getAnnualizedGain() {
        return AnnualizedGain;
    }

    public String getHoldingsGain() {
        return HoldingsGain;
    }

    public String getHoldingsGainPercentRealtime() {
        return HoldingsGainPercentRealtime;
    }

    public String getHoldingsGainRealtime() {
        return HoldingsGainRealtime;
    }

    public String getMoreInfo() {
        return MoreInfo;
    }

    public String getOrderBookRealtime() {
        return OrderBookRealtime;
    }

    public String getMarketCapitalization() {
        return MarketCapitalization;
    }

    public String getMarketCapRealtime() {
        return MarketCapRealtime;
    }

    public String getEBITDA() {
        return EBITDA;
    }

    public String getChangeFromYearLow() {
        return ChangeFromYearLow;
    }

    public String getPercentChangeFromYearLow() {
        return PercentChangeFromYearLow;
    }

    public String getLastTradeRealtimeWithTime() {
        return LastTradeRealtimeWithTime;
    }

    public String getChangePercentRealtime() {
        return ChangePercentRealtime;
    }

    public String getChangeFromYearHigh() {
        return ChangeFromYearHigh;
    }

    public String getPercentChangeFromYearHigh() {
        return PercentChangeFromYearHigh;
    }

    public String getLastTradeWithTime() {
        return LastTradeWithTime;
    }

    public String getLastTradePriceOnly() {
        return LastTradePriceOnly;
    }

    public String getHighLimit() {
        return HighLimit;
    }

    public String getLowLimit() {
        return LowLimit;
    }

    public String getDaysRange() {
        return DaysRange;
    }

    public String getDaysRangeRealtime() {
        return DaysRangeRealtime;
    }

    public String getFiftyDaysMovingAverage() {
        return FiftyDaysMovingAverage;
    }

    public String getTwoHundredDaysMovingAverage() {
        return TwoHundredDaysMovingAverage;
    }

    public String getChangeFromTwoHundredDaysMovingAverage() {
        return ChangeFromTwoHundredDaysMovingAverage;
    }

    public String getPercentChangeFromTwoHundredDaysMovingAverage() {
        return PercentChangeFromTwoHundredDaysMovingAverage;
    }

    public String getChangeFromFiftyDaysMovingAverage() {
        return ChangeFromFiftyDaysMovingAverage;
    }

    public String getPercentChangeFromFiftyDaysMovingAverage() {
        return PercentChangeFromFiftyDaysMovingAverage;
    }

    public String getName() {
        return Name;
    }

    public String getNotes() {
        return Notes;
    }

    public String getOpen() {
        return Open;
    }

    public String getPreviousClose() {
        return PreviousClose;
    }

    public String getPricePaid() {
        return PricePaid;
    }

    public String getChangePercent() {
        return ChangePercent;
    }

    public String getPriceSales() {
        return PriceSales;
    }

    public String getPriceBook() {
        return PriceBook;
    }

    public String getExDividendDate() {
        return ExDividendDate;
    }

    public String getPERatio() {
        return PERatio;
    }

    public String getDividendPayDate() {
        return DividendPayDate;
    }

    public String getPERatioRealtime() {
        return PERatioRealtime;
    }

    public String getPEGRatio() {
        return PEGRatio;
    }

    public String getPriceEPSEstimateCurrentYear() {
        return PriceEPSEstimateCurrentYear;
    }

    public String getPriceEPSEstimateNextYear() {
        return PriceEPSEstimateNextYear;
    }

    public String getSharesOwned() {
        return SharesOwned;
    }

    public String getShortRatio() {
        return ShortRatio;
    }

    public String getLastTradeTime() {
        return LastTradeTime;
    }

    public String getTickerTrend() {
        return TickerTrend;
    }

    public String getOneYearTargetPrice() {
        return OneYearTargetPrice;
    }

    public String getVolume() {
        return Volume;
    }

    public String getHoldingsValue() {
        return HoldingsValue;
    }

    public String getHoldingsValueRealtime() {
        return HoldingsValueRealtime;
    }

    public String getYearRange() {
        return YearRange;
    }

    public String getDaysValueChange() {
        return DaysValueChange;
    }

    public String getDaysValueChangeRealtime() {
        return DaysValueChangeRealtime;
    }

    public String getStockExchange() {
        return StockExchange;
    }

    public String getDividendYield() {
        return DividendYield;
    }

    public String getPercentChange() {
        return PercentChange;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeString(Ask);
        dest.writeString(AverageDailyVolume);
        dest.writeString(Bid);
        dest.writeString(AskRealtime);
        dest.writeString(BidRealtime);
        dest.writeString(BookValue);
        dest.writeString(ChangePercentChange);
        dest.writeString(Change);
        dest.writeString(Commission);
        dest.writeString(Currency);
        dest.writeString(ChangeRealtime);
        dest.writeString(AfterHoursChangeRealtime);
        dest.writeString(DividendShare);
        dest.writeString(LastTradeDate);
        dest.writeString(TradeDate);
        dest.writeString(EarningsShare);
        dest.writeString(ErrorIndicationreturnedforsymbolchangedinvalid);
        dest.writeString(EPSEstimateCurrentYear);
        dest.writeString(EPSEstimateNextYear);
        dest.writeString(EPSEstimateNextQuarter);
        dest.writeString(DaysLow);
        dest.writeString(DaysHigh);
        dest.writeString(YearLow);
        dest.writeString(YearHigh);
        dest.writeString(HoldingsGainPercent);
        dest.writeString(AnnualizedGain);
        dest.writeString(HoldingsGain);
        dest.writeString(HoldingsGainPercentRealtime);
        dest.writeString(HoldingsGainRealtime);
        dest.writeString(MoreInfo);
        dest.writeString(OrderBookRealtime);
        dest.writeString(MarketCapitalization);
        dest.writeString(MarketCapRealtime);
        dest.writeString(EBITDA);
        dest.writeString(ChangeFromYearLow);
        dest.writeString(PercentChangeFromYearLow);
        dest.writeString(LastTradeRealtimeWithTime);
        dest.writeString(ChangePercentRealtime);
        dest.writeString(ChangeFromYearHigh);
        dest.writeString(PercentChangeFromYearHigh);
        dest.writeString(LastTradeWithTime);
        dest.writeString(LastTradePriceOnly);
        dest.writeString(HighLimit);
        dest.writeString(LowLimit);
        dest.writeString(DaysRange);
        dest.writeString(DaysRangeRealtime);
        dest.writeString(FiftyDaysMovingAverage);
        dest.writeString(TwoHundredDaysMovingAverage);
        dest.writeString(ChangeFromTwoHundredDaysMovingAverage);
        dest.writeString(PercentChangeFromTwoHundredDaysMovingAverage);
        dest.writeString(ChangeFromFiftyDaysMovingAverage);
        dest.writeString(PercentChangeFromFiftyDaysMovingAverage);
        dest.writeString(Name);
        dest.writeString(Notes);
        dest.writeString(Open);
        dest.writeString(PreviousClose);
        dest.writeString(PricePaid);
        dest.writeString(ChangePercent);
        dest.writeString(PriceSales);
        dest.writeString(PriceBook);
        dest.writeString(ExDividendDate);
        dest.writeString(PERatio);
        dest.writeString(DividendPayDate);
        dest.writeString(PERatioRealtime);
        dest.writeString(PEGRatio);
        dest.writeString(PriceEPSEstimateCurrentYear);
        dest.writeString(PriceEPSEstimateNextYear);
        dest.writeString(SharesOwned);
        dest.writeString(ShortRatio);
        dest.writeString(LastTradeTime);
        dest.writeString(TickerTrend);
        dest.writeString(OneYearTargetPrice);
        dest.writeString(Volume);
        dest.writeString(HoldingsValue);
        dest.writeString(HoldingsValueRealtime);
        dest.writeString(YearRange);
        dest.writeString(DaysValueChange);
        dest.writeString(DaysValueChangeRealtime);
        dest.writeString(StockExchange);
        dest.writeString(DividendYield);
        dest.writeString(PercentChange);
    }
}