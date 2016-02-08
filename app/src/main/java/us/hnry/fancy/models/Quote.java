package us.hnry.fancy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Henry on 2/7/2016.
 *
 */
public class Quote {


    private List<SingleQuote> results;

    public List<SingleQuote> getResults() {
        return results;
    }

    public void setResults(List<SingleQuote> results) {
        this.results = results;
    }

    public static class SingleQuote implements Parcelable{
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
        public String symbol;
        public String Ask;
        public String AverageDailyVolume;
        public String Bid;
        public Object AskRealtime;
        public Object BidRealtime;
        public String BookValue;
        public String ChangePercentChange;
        public String Change;
        public Object Commission;
        public String Currency;
        public Object ChangeRealtime;
        public Object AfterHoursChangeRealtime;
        public Object DividendShare;
        public String LastTradeDate;
        public Object TradeDate;
        public String EarningsShare;
        public Object ErrorIndicationreturnedforsymbolchangedinvalid;
        public String EPSEstimateCurrentYear;
        public String EPSEstimateNextYear;
        public String EPSEstimateNextQuarter;
        public String DaysLow;
        public String DaysHigh;
        public String YearLow;
        public String YearHigh;
        public Object HoldingsGainPercent;
        public Object AnnualizedGain;
        public Object HoldingsGain;
        public Object HoldingsGainPercentRealtime;
        public Object HoldingsGainRealtime;
        public Object MoreInfo;
        public Object OrderBookRealtime;
        public String MarketCapitalization;
        public Object MarketCapRealtime;
        public String EBITDA;
        public String ChangeFromYearLow;
        public String PercentChangeFromYearLow;
        public Object LastTradeRealtimeWithTime;
        public Object ChangePercentRealtime;
        public String ChangeFromYearHigh;
        public String PercebtChangeFromYearHigh;
        public String LastTradeWithTime;
        public String LastTradePriceOnly;
        public Object HighLimit;
        public Object LowLimit;
        public String DaysRange;
        public Object DaysRangeRealtime;
        public String FiftydayMovingAverage;
        public String TwoHundreddayMovingAverage;
        public String ChangeFromTwoHundreddayMovingAverage;
        public String PercentChangeFromTwoHundreddayMovingAverage;
        public String ChangeFromFiftydayMovingAverage;
        public String PercentChangeFromFiftydayMovingAverage;
        public String Name;
        public Object Notes;
        public String Open;
        public String PreviousClose;
        public Object PricePaid;
        public String ChangeinPercent;
        public String PriceSales;
        public String PriceBook;
        public Object ExDividendDate;
        public String PERatio;
        public Object DividendPayDate;
        public Object PERatioRealtime;
        public String PEGRatio;
        public String PriceEPSEstimateCurrentYear;
        public String PriceEPSEstimateNextYear;
        public Object SharesOwned;
        public String ShortRatio;
        public String LastTradeTime;
        public Object TickerTrend;
        public String OneyrTargetPrice;
        public String Volume;
        public Object HoldingsValue;
        public Object HoldingsValueRealtime;
        public String YearRange;
        public Object DaysValueChange;
        public Object DaysValueChangeRealtime;
        public String StockExchange;
        public Object DividendYield;
        public String PercentChange;

        public SingleQuote(String symbol, String ask, String averageDailyVolume, String bid, Object askRealtime, Object bidRealtime, String bookValue, String changePercentChange, String change, Object commission, String currency, Object changeRealtime, Object afterHoursChangeRealtime, Object dividendShare, String lastTradeDate, Object tradeDate, String earningsShare, Object errorIndicationreturnedforsymbolchangedinvalid, String EPSEstimateCurrentYear, String EPSEstimateNextYear, String EPSEstimateNextQuarter, String daysLow, String daysHigh, String yearLow, String yearHigh, Object holdingsGainPercent, Object annualizedGain, Object holdingsGain, Object holdingsGainPercentRealtime, Object holdingsGainRealtime, Object moreInfo, Object orderBookRealtime, String marketCapitalization, Object marketCapRealtime, String EBITDA, String changeFromYearLow, String percentChangeFromYearLow, Object lastTradeRealtimeWithTime, Object changePercentRealtime, String changeFromYearHigh, String percebtChangeFromYearHigh, String lastTradeWithTime, String lastTradePriceOnly, Object highLimit, Object lowLimit, String daysRange, Object daysRangeRealtime, String fiftydayMovingAverage, String twoHundreddayMovingAverage, String changeFromTwoHundreddayMovingAverage, String percentChangeFromTwoHundreddayMovingAverage, String changeFromFiftydayMovingAverage, String percentChangeFromFiftydayMovingAverage, String name, Object notes, String open, String previousClose, Object pricePaid, String changeinPercent, String priceSales, String priceBook, Object exDividendDate, String PERatio, Object dividendPayDate, Object PERatioRealtime, String PEGRatio, String priceEPSEstimateCurrentYear, String priceEPSEstimateNextYear, Object sharesOwned, String shortRatio, String lastTradeTime, Object tickerTrend, String oneyrTargetPrice, String volume, Object holdingsValue, Object holdingsValueRealtime, String yearRange, Object daysValueChange, Object daysValueChangeRealtime, String stockExchange, Object dividendYield, String percentChange) {
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
            PercebtChangeFromYearHigh = percebtChangeFromYearHigh;
            LastTradeWithTime = lastTradeWithTime;
            LastTradePriceOnly = lastTradePriceOnly;
            HighLimit = highLimit;
            LowLimit = lowLimit;
            DaysRange = daysRange;
            DaysRangeRealtime = daysRangeRealtime;
            FiftydayMovingAverage = fiftydayMovingAverage;
            TwoHundreddayMovingAverage = twoHundreddayMovingAverage;
            ChangeFromTwoHundreddayMovingAverage = changeFromTwoHundreddayMovingAverage;
            PercentChangeFromTwoHundreddayMovingAverage = percentChangeFromTwoHundreddayMovingAverage;
            ChangeFromFiftydayMovingAverage = changeFromFiftydayMovingAverage;
            PercentChangeFromFiftydayMovingAverage = percentChangeFromFiftydayMovingAverage;
            Name = name;
            Notes = notes;
            Open = open;
            PreviousClose = previousClose;
            PricePaid = pricePaid;
            ChangeinPercent = changeinPercent;
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
            OneyrTargetPrice = oneyrTargetPrice;
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
            BookValue = in.readString();
            ChangePercentChange = in.readString();
            Change = in.readString();
            Currency = in.readString();
            LastTradeDate = in.readString();
            EarningsShare = in.readString();
            EPSEstimateCurrentYear = in.readString();
            EPSEstimateNextYear = in.readString();
            EPSEstimateNextQuarter = in.readString();
            DaysLow = in.readString();
            DaysHigh = in.readString();
            YearLow = in.readString();
            YearHigh = in.readString();
            MarketCapitalization = in.readString();
            EBITDA = in.readString();
            ChangeFromYearLow = in.readString();
            PercentChangeFromYearLow = in.readString();
            ChangeFromYearHigh = in.readString();
            PercebtChangeFromYearHigh = in.readString();
            LastTradeWithTime = in.readString();
            LastTradePriceOnly = in.readString();
            DaysRange = in.readString();
            FiftydayMovingAverage = in.readString();
            TwoHundreddayMovingAverage = in.readString();
            ChangeFromTwoHundreddayMovingAverage = in.readString();
            PercentChangeFromTwoHundreddayMovingAverage = in.readString();
            ChangeFromFiftydayMovingAverage = in.readString();
            PercentChangeFromFiftydayMovingAverage = in.readString();
            Name = in.readString();
            Open = in.readString();
            PreviousClose = in.readString();
            ChangeinPercent = in.readString();
            PriceSales = in.readString();
            PriceBook = in.readString();
            PERatio = in.readString();
            PEGRatio = in.readString();
            PriceEPSEstimateCurrentYear = in.readString();
            PriceEPSEstimateNextYear = in.readString();
            ShortRatio = in.readString();
            LastTradeTime = in.readString();
            OneyrTargetPrice = in.readString();
            Volume = in.readString();
            YearRange = in.readString();
            StockExchange = in.readString();
            PercentChange = in.readString();
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String ask) {
            Ask = ask;
        }

        public String getAverageDailyVolume() {
            return AverageDailyVolume;
        }

        public void setAverageDailyVolume(String averageDailyVolume) {
            AverageDailyVolume = averageDailyVolume;
        }

        public String getBid() {
            return Bid;
        }

        public void setBid(String bid) {
            Bid = bid;
        }

        public Object getAskRealtime() {
            return AskRealtime;
        }

        public void setAskRealtime(Object askRealtime) {
            AskRealtime = askRealtime;
        }

        public Object getBidRealtime() {
            return BidRealtime;
        }

        public void setBidRealtime(Object bidRealtime) {
            BidRealtime = bidRealtime;
        }

        public String getBookValue() {
            return BookValue;
        }

        public void setBookValue(String bookValue) {
            BookValue = bookValue;
        }

        public String getChangePercentChange() {
            return ChangePercentChange;
        }

        public void setChangePercentChange(String changePercentChange) {
            ChangePercentChange = changePercentChange;
        }

        public String getChange() {
            return Change;
        }

        public void setChange(String change) {
            Change = change;
        }

        public Object getCommission() {
            return Commission;
        }

        public void setCommission(Object commission) {
            Commission = commission;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public Object getChangeRealtime() {
            return ChangeRealtime;
        }

        public void setChangeRealtime(Object changeRealtime) {
            ChangeRealtime = changeRealtime;
        }

        public Object getAfterHoursChangeRealtime() {
            return AfterHoursChangeRealtime;
        }

        public void setAfterHoursChangeRealtime(Object afterHoursChangeRealtime) {
            AfterHoursChangeRealtime = afterHoursChangeRealtime;
        }

        public Object getDividendShare() {
            return DividendShare;
        }

        public void setDividendShare(Object dividendShare) {
            DividendShare = dividendShare;
        }

        public String getLastTradeDate() {
            return LastTradeDate;
        }

        public void setLastTradeDate(String lastTradeDate) {
            LastTradeDate = lastTradeDate;
        }

        public Object getTradeDate() {
            return TradeDate;
        }

        public void setTradeDate(Object tradeDate) {
            TradeDate = tradeDate;
        }

        public String getEarningsShare() {
            return EarningsShare;
        }

        public void setEarningsShare(String earningsShare) {
            EarningsShare = earningsShare;
        }

        public Object getErrorIndicationreturnedforsymbolchangedinvalid() {
            return ErrorIndicationreturnedforsymbolchangedinvalid;
        }

        public void setErrorIndicationreturnedforsymbolchangedinvalid(Object errorIndicationreturnedforsymbolchangedinvalid) {
            ErrorIndicationreturnedforsymbolchangedinvalid = errorIndicationreturnedforsymbolchangedinvalid;
        }

        public String getEPSEstimateCurrentYear() {
            return EPSEstimateCurrentYear;
        }

        public void setEPSEstimateCurrentYear(String EPSEstimateCurrentYear) {
            this.EPSEstimateCurrentYear = EPSEstimateCurrentYear;
        }

        public String getEPSEstimateNextYear() {
            return EPSEstimateNextYear;
        }

        public void setEPSEstimateNextYear(String EPSEstimateNextYear) {
            this.EPSEstimateNextYear = EPSEstimateNextYear;
        }

        public String getEPSEstimateNextQuarter() {
            return EPSEstimateNextQuarter;
        }

        public void setEPSEstimateNextQuarter(String EPSEstimateNextQuarter) {
            this.EPSEstimateNextQuarter = EPSEstimateNextQuarter;
        }

        public String getDaysLow() {
            return DaysLow;
        }

        public void setDaysLow(String daysLow) {
            DaysLow = daysLow;
        }

        public String getDaysHigh() {
            return DaysHigh;
        }

        public void setDaysHigh(String daysHigh) {
            DaysHigh = daysHigh;
        }

        public String getYearLow() {
            return YearLow;
        }

        public void setYearLow(String yearLow) {
            YearLow = yearLow;
        }

        public String getYearHigh() {
            return YearHigh;
        }

        public void setYearHigh(String yearHigh) {
            YearHigh = yearHigh;
        }

        public Object getHoldingsGainPercent() {
            return HoldingsGainPercent;
        }

        public void setHoldingsGainPercent(Object holdingsGainPercent) {
            HoldingsGainPercent = holdingsGainPercent;
        }

        public Object getAnnualizedGain() {
            return AnnualizedGain;
        }

        public void setAnnualizedGain(Object annualizedGain) {
            AnnualizedGain = annualizedGain;
        }

        public Object getHoldingsGain() {
            return HoldingsGain;
        }

        public void setHoldingsGain(Object holdingsGain) {
            HoldingsGain = holdingsGain;
        }

        public Object getHoldingsGainPercentRealtime() {
            return HoldingsGainPercentRealtime;
        }

        public void setHoldingsGainPercentRealtime(Object holdingsGainPercentRealtime) {
            HoldingsGainPercentRealtime = holdingsGainPercentRealtime;
        }

        public Object getHoldingsGainRealtime() {
            return HoldingsGainRealtime;
        }

        public void setHoldingsGainRealtime(Object holdingsGainRealtime) {
            HoldingsGainRealtime = holdingsGainRealtime;
        }

        public Object getMoreInfo() {
            return MoreInfo;
        }

        public void setMoreInfo(Object moreInfo) {
            MoreInfo = moreInfo;
        }

        public Object getOrderBookRealtime() {
            return OrderBookRealtime;
        }

        public void setOrderBookRealtime(Object orderBookRealtime) {
            OrderBookRealtime = orderBookRealtime;
        }

        public String getMarketCapitalization() {
            return MarketCapitalization;
        }

        public void setMarketCapitalization(String marketCapitalization) {
            MarketCapitalization = marketCapitalization;
        }

        public Object getMarketCapRealtime() {
            return MarketCapRealtime;
        }

        public void setMarketCapRealtime(Object marketCapRealtime) {
            MarketCapRealtime = marketCapRealtime;
        }

        public String getEBITDA() {
            return EBITDA;
        }

        public void setEBITDA(String EBITDA) {
            this.EBITDA = EBITDA;
        }

        public String getChangeFromYearLow() {
            return ChangeFromYearLow;
        }

        public void setChangeFromYearLow(String changeFromYearLow) {
            ChangeFromYearLow = changeFromYearLow;
        }

        public String getPercentChangeFromYearLow() {
            return PercentChangeFromYearLow;
        }

        public void setPercentChangeFromYearLow(String percentChangeFromYearLow) {
            PercentChangeFromYearLow = percentChangeFromYearLow;
        }

        public Object getLastTradeRealtimeWithTime() {
            return LastTradeRealtimeWithTime;
        }

        public void setLastTradeRealtimeWithTime(Object lastTradeRealtimeWithTime) {
            LastTradeRealtimeWithTime = lastTradeRealtimeWithTime;
        }

        public Object getChangePercentRealtime() {
            return ChangePercentRealtime;
        }

        public void setChangePercentRealtime(Object changePercentRealtime) {
            ChangePercentRealtime = changePercentRealtime;
        }

        public String getChangeFromYearHigh() {
            return ChangeFromYearHigh;
        }

        public void setChangeFromYearHigh(String changeFromYearHigh) {
            ChangeFromYearHigh = changeFromYearHigh;
        }

        public String getPercebtChangeFromYearHigh() {
            return PercebtChangeFromYearHigh;
        }

        public void setPercebtChangeFromYearHigh(String percebtChangeFromYearHigh) {
            PercebtChangeFromYearHigh = percebtChangeFromYearHigh;
        }

        public String getLastTradeWithTime() {
            return LastTradeWithTime;
        }

        public void setLastTradeWithTime(String lastTradeWithTime) {
            LastTradeWithTime = lastTradeWithTime;
        }

        public String getLastTradePriceOnly() {
            return LastTradePriceOnly;
        }

        public void setLastTradePriceOnly(String lastTradePriceOnly) {
            LastTradePriceOnly = lastTradePriceOnly;
        }

        public Object getHighLimit() {
            return HighLimit;
        }

        public void setHighLimit(Object highLimit) {
            HighLimit = highLimit;
        }

        public Object getLowLimit() {
            return LowLimit;
        }

        public void setLowLimit(Object lowLimit) {
            LowLimit = lowLimit;
        }

        public String getDaysRange() {
            return DaysRange;
        }

        public void setDaysRange(String daysRange) {
            DaysRange = daysRange;
        }

        public Object getDaysRangeRealtime() {
            return DaysRangeRealtime;
        }

        public void setDaysRangeRealtime(Object daysRangeRealtime) {
            DaysRangeRealtime = daysRangeRealtime;
        }

        public String getFiftydayMovingAverage() {
            return FiftydayMovingAverage;
        }

        public void setFiftydayMovingAverage(String fiftydayMovingAverage) {
            FiftydayMovingAverage = fiftydayMovingAverage;
        }

        public String getTwoHundreddayMovingAverage() {
            return TwoHundreddayMovingAverage;
        }

        public void setTwoHundreddayMovingAverage(String twoHundreddayMovingAverage) {
            TwoHundreddayMovingAverage = twoHundreddayMovingAverage;
        }

        public String getChangeFromTwoHundreddayMovingAverage() {
            return ChangeFromTwoHundreddayMovingAverage;
        }

        public void setChangeFromTwoHundreddayMovingAverage(String changeFromTwoHundreddayMovingAverage) {
            ChangeFromTwoHundreddayMovingAverage = changeFromTwoHundreddayMovingAverage;
        }

        public String getPercentChangeFromTwoHundreddayMovingAverage() {
            return PercentChangeFromTwoHundreddayMovingAverage;
        }

        public void setPercentChangeFromTwoHundreddayMovingAverage(String percentChangeFromTwoHundreddayMovingAverage) {
            PercentChangeFromTwoHundreddayMovingAverage = percentChangeFromTwoHundreddayMovingAverage;
        }

        public String getChangeFromFiftydayMovingAverage() {
            return ChangeFromFiftydayMovingAverage;
        }

        public void setChangeFromFiftydayMovingAverage(String changeFromFiftydayMovingAverage) {
            ChangeFromFiftydayMovingAverage = changeFromFiftydayMovingAverage;
        }

        public String getPercentChangeFromFiftydayMovingAverage() {
            return PercentChangeFromFiftydayMovingAverage;
        }

        public void setPercentChangeFromFiftydayMovingAverage(String percentChangeFromFiftydayMovingAverage) {
            PercentChangeFromFiftydayMovingAverage = percentChangeFromFiftydayMovingAverage;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Object getNotes() {
            return Notes;
        }

        public void setNotes(Object notes) {
            Notes = notes;
        }

        public String getOpen() {
            return Open;
        }

        public void setOpen(String open) {
            Open = open;
        }

        public String getPreviousClose() {
            return PreviousClose;
        }

        public void setPreviousClose(String previousClose) {
            PreviousClose = previousClose;
        }

        public Object getPricePaid() {
            return PricePaid;
        }

        public void setPricePaid(Object pricePaid) {
            PricePaid = pricePaid;
        }

        public String getChangeinPercent() {
            return ChangeinPercent;
        }

        public void setChangeinPercent(String changeinPercent) {
            ChangeinPercent = changeinPercent;
        }

        public String getPriceSales() {
            return PriceSales;
        }

        public void setPriceSales(String priceSales) {
            PriceSales = priceSales;
        }

        public String getPriceBook() {
            return PriceBook;
        }

        public void setPriceBook(String priceBook) {
            PriceBook = priceBook;
        }

        public Object getExDividendDate() {
            return ExDividendDate;
        }

        public void setExDividendDate(Object exDividendDate) {
            ExDividendDate = exDividendDate;
        }

        public String getPERatio() {
            return PERatio;
        }

        public void setPERatio(String PERatio) {
            this.PERatio = PERatio;
        }

        public Object getDividendPayDate() {
            return DividendPayDate;
        }

        public void setDividendPayDate(Object dividendPayDate) {
            DividendPayDate = dividendPayDate;
        }

        public Object getPERatioRealtime() {
            return PERatioRealtime;
        }

        public void setPERatioRealtime(Object PERatioRealtime) {
            this.PERatioRealtime = PERatioRealtime;
        }

        public String getPEGRatio() {
            return PEGRatio;
        }

        public void setPEGRatio(String PEGRatio) {
            this.PEGRatio = PEGRatio;
        }

        public String getPriceEPSEstimateCurrentYear() {
            return PriceEPSEstimateCurrentYear;
        }

        public void setPriceEPSEstimateCurrentYear(String priceEPSEstimateCurrentYear) {
            PriceEPSEstimateCurrentYear = priceEPSEstimateCurrentYear;
        }

        public String getPriceEPSEstimateNextYear() {
            return PriceEPSEstimateNextYear;
        }

        public void setPriceEPSEstimateNextYear(String priceEPSEstimateNextYear) {
            PriceEPSEstimateNextYear = priceEPSEstimateNextYear;
        }

        public Object getSharesOwned() {
            return SharesOwned;
        }

        public void setSharesOwned(Object sharesOwned) {
            SharesOwned = sharesOwned;
        }

        public String getShortRatio() {
            return ShortRatio;
        }

        public void setShortRatio(String shortRatio) {
            ShortRatio = shortRatio;
        }

        public String getLastTradeTime() {
            return LastTradeTime;
        }

        public void setLastTradeTime(String lastTradeTime) {
            LastTradeTime = lastTradeTime;
        }

        public Object getTickerTrend() {
            return TickerTrend;
        }

        public void setTickerTrend(Object tickerTrend) {
            TickerTrend = tickerTrend;
        }

        public String getOneyrTargetPrice() {
            return OneyrTargetPrice;
        }

        public void setOneyrTargetPrice(String oneyrTargetPrice) {
            OneyrTargetPrice = oneyrTargetPrice;
        }

        public String getVolume() {
            return Volume;
        }

        public void setVolume(String volume) {
            Volume = volume;
        }

        public Object getHoldingsValue() {
            return HoldingsValue;
        }

        public void setHoldingsValue(Object holdingsValue) {
            HoldingsValue = holdingsValue;
        }

        public Object getHoldingsValueRealtime() {
            return HoldingsValueRealtime;
        }

        public void setHoldingsValueRealtime(Object holdingsValueRealtime) {
            HoldingsValueRealtime = holdingsValueRealtime;
        }

        public String getYearRange() {
            return YearRange;
        }

        public void setYearRange(String yearRange) {
            YearRange = yearRange;
        }

        public Object getDaysValueChange() {
            return DaysValueChange;
        }

        public void setDaysValueChange(Object daysValueChange) {
            DaysValueChange = daysValueChange;
        }

        public Object getDaysValueChangeRealtime() {
            return DaysValueChangeRealtime;
        }

        public void setDaysValueChangeRealtime(Object daysValueChangeRealtime) {
            DaysValueChangeRealtime = daysValueChangeRealtime;
        }

        public String getStockExchange() {
            return StockExchange;
        }

        public void setStockExchange(String stockExchange) {
            StockExchange = stockExchange;
        }

        public Object getDividendYield() {
            return DividendYield;
        }

        public void setDividendYield(Object dividendYield) {
            DividendYield = dividendYield;
        }

        public String getPercentChange() {
            return PercentChange;
        }

        public void setPercentChange(String percentChange) {
            PercentChange = percentChange;
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
            dest.writeString(BookValue);
            dest.writeString(ChangePercentChange);
            dest.writeString(Change);
            dest.writeString(Currency);
            dest.writeString(LastTradeDate);
            dest.writeString(EarningsShare);
            dest.writeString(EPSEstimateCurrentYear);
            dest.writeString(EPSEstimateNextYear);
            dest.writeString(EPSEstimateNextQuarter);
            dest.writeString(DaysLow);
            dest.writeString(DaysHigh);
            dest.writeString(YearLow);
            dest.writeString(YearHigh);
            dest.writeString(MarketCapitalization);
            dest.writeString(EBITDA);
            dest.writeString(ChangeFromYearLow);
            dest.writeString(PercentChangeFromYearLow);
            dest.writeString(ChangeFromYearHigh);
            dest.writeString(PercebtChangeFromYearHigh);
            dest.writeString(LastTradeWithTime);
            dest.writeString(LastTradePriceOnly);
            dest.writeString(DaysRange);
            dest.writeString(FiftydayMovingAverage);
            dest.writeString(TwoHundreddayMovingAverage);
            dest.writeString(ChangeFromTwoHundreddayMovingAverage);
            dest.writeString(PercentChangeFromTwoHundreddayMovingAverage);
            dest.writeString(ChangeFromFiftydayMovingAverage);
            dest.writeString(PercentChangeFromFiftydayMovingAverage);
            dest.writeString(Name);
            dest.writeString(Open);
            dest.writeString(PreviousClose);
            dest.writeString(ChangeinPercent);
            dest.writeString(PriceSales);
            dest.writeString(PriceBook);
            dest.writeString(PERatio);
            dest.writeString(PEGRatio);
            dest.writeString(PriceEPSEstimateCurrentYear);
            dest.writeString(PriceEPSEstimateNextYear);
            dest.writeString(ShortRatio);
            dest.writeString(LastTradeTime);
            dest.writeString(OneyrTargetPrice);
            dest.writeString(Volume);
            dest.writeString(YearRange);
            dest.writeString(StockExchange);
            dest.writeString(PercentChange);
        }
    }
}
