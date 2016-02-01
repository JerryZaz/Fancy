package us.hnry.fancy.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 1/31/2016.
 * Parcelable class to store stock information fetched from API.
 */
public class Stock implements Parcelable {

    static final String QUOTE_SYMBOL = "symbol";
    static final String QUOTE_ASK = "Ask";
    static final String QUOTE_AVERAGE_DAILY_VOLUME = "AverageDailyVolume";
    static final String QUOTE_BID = "Bid";
    static final String QUOTE_ASK_REALTIME = "AskRealtime";
    static final String QUOTE_BID_REALTIME = "BidRealtime";
    static final String QUOTE_BOOK_VALUE = "BookValue";
    static final String QUOTE_CHANGE = "Change";
    static final String QUOTE_CURRENCY = "Currency";
    static final String QUOTE_CHANGE_REALTIME = "ChangeRealtime";
    static final String QUOTE_LAST_TRADE_DATE = "LastTradeDate";
    static final String QUOTE_EARNINGS_SHARE = "EarningsShare";
    static final String QUOTE_EPS_ESTIMATE_CURRENT_YEAR = "EPSEstimateCurrentYear";
    static final String QUOTE_EPS_ESTIMATE_NEXT_YEAR = "EPSEstimateNextYear";
    static final String QUOTE_EPS_ESTIMATE_NEXT_QUARTER = "EPSEstimateNextQuarter";
    static final String QUOTE_DAYS_LOW = "DaysLow";
    static final String QUOTE_DAYS_HIGH = "DaysHigh";
    static final String QUOTE_YEAR_LOW = "YearLow";
    static final String QUOTE_YEAR_HIGH = "YearHigh";
    static final String QUOTE_CHANGE_FROM_YEAR_LOW = "ChangeFromYearLow";
    static final String QUOTE_PERCENT_CHANGE_FROM_YEAR_LOW = "PercentChangeFromYearLow";
    static final String QUOTE_PERCENT_CHANGE_FROM_YEAR_HIGH = "PercebtChangeFromYearHigh";
    static final String QUOTE_LAST_TRADE_PRICE_ONLY = "LastTradePriceOnly";
    static final String QUOTE_FIFTY_DAY_MOVING_AVERAGE = "FiftydayMovingAverage";
    static final String QUOTE_TWO_HUNDRED_DAY_MOVING_AVERAGE = "TwoHundreddayMovingAverage";
    static final String QUOTE_NAME = "Name";
    static final String QUOTE_OPEN = "Open";
    static final String QUOTE_PREVIOUS_CLOSE = "PreviousClose";
    static final String QUOTE_TICKER_TREND = "TickerTrend";

    private String symbol;
    private double Ask;
    private long AverageDailyVolume;
    private double Bid;
    private double AskRealtime;
    private double BidRealtime;
    private double BookValue;
    private double Change;
    private String Currency;
    private double ChangeRealtime;
    private String LastTradeDate;
    private double EarningsShare;
    private double EPSEstimateCurrentYear;
    private double EPSEstimateNextYear;
    private double EPSEstimateNextQuarter;
    private double DaysLow;
    private double DaysHigh;
    private double YearLow;
    private double YearHigh;
    private double ChangeFromYearLow;
    private String PercentChangeFromYearLow;
    private String PercebtChangeFromYearHigh;
    private double LastTradePriceOnly;
    private double FiftydayMovingAverage;
    private double TwoHundreddayMovingAverage;
    private String Name;
    private double Open;
    private double PreviousClose;
    private String TickerTrend;


    public Stock(String symbol, double ask, long averageDailyVolume, double bid, double askRealtime, double bidRealtime, double bookValue, double change, String currency, double changeRealtime, String lastTradeDate, double earningsShare, double EPSEstimateCurrentYear, double EPSEstimateNextYear, double EPSEstimateNextQuarter, double daysLow, double daysHigh, double yearLow, double yearHigh, double changeFromYearLow, String percentChangeFromYearLow, String percebtChangeFromYearHigh, double lastTradePriceOnly, double fiftydayMovingAverage, double twoHundreddayMovingAverage, String name, double open, double previousClose, String tickerTrend) {
        this.symbol = symbol;
        Ask = ask;
        AverageDailyVolume = averageDailyVolume;
        Bid = bid;
        AskRealtime = askRealtime;
        BidRealtime = bidRealtime;
        BookValue = bookValue;
        Change = change;
        Currency = currency;
        ChangeRealtime = changeRealtime;
        LastTradeDate = lastTradeDate;
        EarningsShare = earningsShare;
        this.EPSEstimateCurrentYear = EPSEstimateCurrentYear;
        this.EPSEstimateNextYear = EPSEstimateNextYear;
        this.EPSEstimateNextQuarter = EPSEstimateNextQuarter;
        DaysLow = daysLow;
        DaysHigh = daysHigh;
        YearLow = yearLow;
        YearHigh = yearHigh;
        ChangeFromYearLow = changeFromYearLow;
        PercentChangeFromYearLow = percentChangeFromYearLow;
        PercebtChangeFromYearHigh = percebtChangeFromYearHigh;
        LastTradePriceOnly = lastTradePriceOnly;
        FiftydayMovingAverage = fiftydayMovingAverage;
        TwoHundreddayMovingAverage = twoHundreddayMovingAverage;
        Name = name;
        Open = open;
        PreviousClose = previousClose;
        TickerTrend = tickerTrend;
    }

    protected Stock(Parcel in) {
        symbol = in.readString();
        Ask = in.readDouble();
        AverageDailyVolume = in.readLong();
        Bid = in.readDouble();
        AskRealtime = in.readDouble();
        BidRealtime = in.readDouble();
        BookValue = in.readDouble();
        Change = in.readDouble();
        Currency = in.readString();
        ChangeRealtime = in.readDouble();
        LastTradeDate = in.readString();
        EarningsShare = in.readDouble();
        EPSEstimateCurrentYear = in.readDouble();
        EPSEstimateNextYear = in.readDouble();
        EPSEstimateNextQuarter = in.readDouble();
        DaysLow = in.readDouble();
        DaysHigh = in.readDouble();
        YearLow = in.readDouble();
        YearHigh = in.readDouble();
        ChangeFromYearLow = in.readDouble();
        PercentChangeFromYearLow = in.readString();
        PercebtChangeFromYearHigh = in.readString();
        LastTradePriceOnly = in.readDouble();
        FiftydayMovingAverage = in.readDouble();
        TwoHundreddayMovingAverage = in.readDouble();
        Name = in.readString();
        Open = in.readDouble();
        PreviousClose = in.readDouble();
        TickerTrend = in.readString();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public double getAsk() {
        return Ask;
    }

    public long getAverageDailyVolume() {
        return AverageDailyVolume;
    }

    public double getBid() {
        return Bid;
    }

    public double getAskRealtime() {
        return AskRealtime;
    }

    public double getBidRealtime() {
        return BidRealtime;
    }

    public double getBookValue() {
        return BookValue;
    }

    public double getChange() {
        return Change;
    }

    public String getCurrency() {
        return Currency;
    }

    public double getChangeRealtime() {
        return ChangeRealtime;
    }

    public String getLastTradeDate() {
        return LastTradeDate;
    }

    public double getEarningsShare() {
        return EarningsShare;
    }

    public double getEPSEstimateCurrentYear() {
        return EPSEstimateCurrentYear;
    }

    public double getEPSEstimateNextYear() {
        return EPSEstimateNextYear;
    }

    public double getEPSEstimateNextQuarter() {
        return EPSEstimateNextQuarter;
    }

    public double getDaysLow() {
        return DaysLow;
    }

    public double getDaysHigh() {
        return DaysHigh;
    }

    public double getYearLow() {
        return YearLow;
    }

    public double getYearHigh() {
        return YearHigh;
    }

    public double getChangeFromYearLow() {
        return ChangeFromYearLow;
    }

    public String getPercentChangeFromYearLow() {
        return PercentChangeFromYearLow;
    }

    public String getPercebtChangeFromYearHigh() {
        return PercebtChangeFromYearHigh;
    }

    public double getLastTradePriceOnly() {
        return LastTradePriceOnly;
    }

    public double getFiftydayMovingAverage() {
        return FiftydayMovingAverage;
    }

    public double getTwoHundreddayMovingAverage() {
        return TwoHundreddayMovingAverage;
    }

    public String getName() {
        return Name;
    }

    public double getOpen() {
        return Open;
    }

    public double getPreviousClose() {
        return PreviousClose;
    }

    public String getTickerTrend() {
        return TickerTrend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeDouble(Ask);
        dest.writeLong(AverageDailyVolume);
        dest.writeDouble(Bid);
        dest.writeDouble(AskRealtime);
        dest.writeDouble(BidRealtime);
        dest.writeDouble(BookValue);
        dest.writeDouble(Change);
        dest.writeString(Currency);
        dest.writeDouble(ChangeRealtime);
        dest.writeString(LastTradeDate);
        dest.writeDouble(EarningsShare);
        dest.writeDouble(EPSEstimateCurrentYear);
        dest.writeDouble(EPSEstimateNextYear);
        dest.writeDouble(EPSEstimateNextQuarter);
        dest.writeDouble(DaysLow);
        dest.writeDouble(DaysHigh);
        dest.writeDouble(YearLow);
        dest.writeDouble(YearHigh);
        dest.writeDouble(ChangeFromYearLow);
        dest.writeString(PercentChangeFromYearLow);
        dest.writeString(PercebtChangeFromYearHigh);
        dest.writeDouble(LastTradePriceOnly);
        dest.writeDouble(FiftydayMovingAverage);
        dest.writeDouble(TwoHundreddayMovingAverage);
        dest.writeString(Name);
        dest.writeDouble(Open);
        dest.writeDouble(PreviousClose);
        dest.writeString(TickerTrend);
    }
}