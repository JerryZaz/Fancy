package us.hnry.fancy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 2/7/2016.
 *
 */
@SuppressWarnings("unused, SpellCheckingInspection")
public class Quote {

    public Query query;

    public static class SingleQuote implements Parcelable {
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
        public String AskRealtime;
        public String BidRealtime;
        public String BookValue;
        public String ChangePercentChange;
        public String Change;
        public String Commission;
        public String Currency;
        public String ChangeRealtime;
        public String AfterHoursChangeRealtime;
        public String DividendShare;
        public String LastTradeDate;
        public String TradeDate;
        public String EarningsShare;
        public String ErrorIndicationreturnedforsymbolchangedinvalid;
        public String EPSEstimateCurrentYear;
        public String EPSEstimateNextYear;
        public String EPSEstimateNextQuarter;
        public String DaysLow;
        public String DaysHigh;
        public String YearLow;
        public String YearHigh;
        public String HoldingsGainPercent;
        public String AnnualizedGain;
        public String HoldingsGain;
        public String HoldingsGainPercentRealtime;
        public String HoldingsGainRealtime;
        public String MoreInfo;
        public String OrderBookRealtime;
        public String MarketCapitalization;
        public String MarketCapRealtime;
        public String EBITDA;
        public String ChangeFromYearLow;
        public String PercentChangeFromYearLow;
        public String LastTradeRealtimeWithTime;
        public String ChangePercentRealtime;
        public String ChangeFromYearHigh;

        @SerializedName("PercebtChangeFromYearHigh")
        public String PercentChangeFromYearHigh;

        public String LastTradeWithTime;
        public String LastTradePriceOnly;
        public String HighLimit;
        public String LowLimit;
        public String DaysRange;
        public String DaysRangeRealtime;

        @SerializedName("FiftydayMovingAverage")
        public String FiftyDaysMovingAverage;

        @SerializedName("TwoHundreddayMovingAverage")
        public String TwoHundredDaysMovingAverage;

        @SerializedName("ChangeFromTwoHundreddayMovingAverage")
        public String ChangeFromTwoHundredDaysMovingAverage;

        @SerializedName("PercentChangeFromTwoHundreddayMovingAverage")
        public String PercentChangeFromTwoHundredDaysMovingAverage;

        @SerializedName("ChangeFromFiftydayMovingAverage")
        public String ChangeFromFiftyDaysMovingAverage;

        @SerializedName("PercentChangeFromFiftydayMovingAverage")
        public String PercentChangeFromFiftyDaysMovingAverage;

        public String Name;
        public String Notes;
        public String Open;
        public String PreviousClose;
        public String PricePaid;

        @SerializedName("ChangeinPercent")
        public String ChangePercent;

        public String PriceSales;
        public String PriceBook;
        public String ExDividendDate;
        public String PERatio;
        public String DividendPayDate;
        public String PERatioRealtime;
        public String PEGRatio;
        public String PriceEPSEstimateCurrentYear;
        public String PriceEPSEstimateNextYear;
        public String SharesOwned;
        public String ShortRatio;
        public String LastTradeTime;
        public String TickerTrend;

        @SerializedName("OneyrTargetPrice")
        public String OneYearTargetPrice;

        public String Volume;
        public String HoldingsValue;
        public String HoldingsValueRealtime;
        public String YearRange;
        public String DaysValueChange;
        public String DaysValueChangeRealtime;
        public String StockExchange;
        public String DividendYield;
        public String PercentChange;

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

        public String getAskRealtime() {
            return AskRealtime;
        }

        public void setAskRealtime(String askRealtime) {
            AskRealtime = askRealtime;
        }

        public String getBidRealtime() {
            return BidRealtime;
        }

        public void setBidRealtime(String bidRealtime) {
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

        public String getCommission() {
            return Commission;
        }

        public void setCommission(String commission) {
            Commission = commission;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public String getChangeRealtime() {
            return ChangeRealtime;
        }

        public void setChangeRealtime(String changeRealtime) {
            ChangeRealtime = changeRealtime;
        }

        public String getAfterHoursChangeRealtime() {
            return AfterHoursChangeRealtime;
        }

        public void setAfterHoursChangeRealtime(String afterHoursChangeRealtime) {
            AfterHoursChangeRealtime = afterHoursChangeRealtime;
        }

        public String getDividendShare() {
            return DividendShare;
        }

        public void setDividendShare(String dividendShare) {
            DividendShare = dividendShare;
        }

        public String getLastTradeDate() {
            return LastTradeDate;
        }

        public void setLastTradeDate(String lastTradeDate) {
            LastTradeDate = lastTradeDate;
        }

        public String getTradeDate() {
            return TradeDate;
        }

        public void setTradeDate(String tradeDate) {
            TradeDate = tradeDate;
        }

        public String getEarningsShare() {
            return EarningsShare;
        }

        public void setEarningsShare(String earningsShare) {
            EarningsShare = earningsShare;
        }

        public String getErrorIndicationreturnedforsymbolchangedinvalid() {
            return ErrorIndicationreturnedforsymbolchangedinvalid;
        }

        public void setErrorIndicationreturnedforsymbolchangedinvalid(String errorIndicationreturnedforsymbolchangedinvalid) {
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

        public String getHoldingsGainPercent() {
            return HoldingsGainPercent;
        }

        public void setHoldingsGainPercent(String holdingsGainPercent) {
            HoldingsGainPercent = holdingsGainPercent;
        }

        public String getAnnualizedGain() {
            return AnnualizedGain;
        }

        public void setAnnualizedGain(String annualizedGain) {
            AnnualizedGain = annualizedGain;
        }

        public String getHoldingsGain() {
            return HoldingsGain;
        }

        public void setHoldingsGain(String holdingsGain) {
            HoldingsGain = holdingsGain;
        }

        public String getHoldingsGainPercentRealtime() {
            return HoldingsGainPercentRealtime;
        }

        public void setHoldingsGainPercentRealtime(String holdingsGainPercentRealtime) {
            HoldingsGainPercentRealtime = holdingsGainPercentRealtime;
        }

        public String getHoldingsGainRealtime() {
            return HoldingsGainRealtime;
        }

        public void setHoldingsGainRealtime(String holdingsGainRealtime) {
            HoldingsGainRealtime = holdingsGainRealtime;
        }

        public String getMoreInfo() {
            return MoreInfo;
        }

        public void setMoreInfo(String moreInfo) {
            MoreInfo = moreInfo;
        }

        public String getOrderBookRealtime() {
            return OrderBookRealtime;
        }

        public void setOrderBookRealtime(String orderBookRealtime) {
            OrderBookRealtime = orderBookRealtime;
        }

        public String getMarketCapitalization() {
            return MarketCapitalization;
        }

        public void setMarketCapitalization(String marketCapitalization) {
            MarketCapitalization = marketCapitalization;
        }

        public String getMarketCapRealtime() {
            return MarketCapRealtime;
        }

        public void setMarketCapRealtime(String marketCapRealtime) {
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

        public String getLastTradeRealtimeWithTime() {
            return LastTradeRealtimeWithTime;
        }

        public void setLastTradeRealtimeWithTime(String lastTradeRealtimeWithTime) {
            LastTradeRealtimeWithTime = lastTradeRealtimeWithTime;
        }

        public String getChangePercentRealtime() {
            return ChangePercentRealtime;
        }

        public void setChangePercentRealtime(String changePercentRealtime) {
            ChangePercentRealtime = changePercentRealtime;
        }

        public String getChangeFromYearHigh() {
            return ChangeFromYearHigh;
        }

        public void setChangeFromYearHigh(String changeFromYearHigh) {
            ChangeFromYearHigh = changeFromYearHigh;
        }

        public String getPercentChangeFromYearHigh() {
            return PercentChangeFromYearHigh;
        }

        public void setPercentChangeFromYearHigh(String percentChangeFromYearHigh) {
            PercentChangeFromYearHigh = percentChangeFromYearHigh;
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

        public String getHighLimit() {
            return HighLimit;
        }

        public void setHighLimit(String highLimit) {
            HighLimit = highLimit;
        }

        public String getLowLimit() {
            return LowLimit;
        }

        public void setLowLimit(String lowLimit) {
            LowLimit = lowLimit;
        }

        public String getDaysRange() {
            return DaysRange;
        }

        public void setDaysRange(String daysRange) {
            DaysRange = daysRange;
        }

        public String getDaysRangeRealtime() {
            return DaysRangeRealtime;
        }

        public void setDaysRangeRealtime(String daysRangeRealtime) {
            DaysRangeRealtime = daysRangeRealtime;
        }

        public String getFiftyDaysMovingAverage() {
            return FiftyDaysMovingAverage;
        }

        public void setFiftyDaysMovingAverage(String fiftyDaysMovingAverage) {
            FiftyDaysMovingAverage = fiftyDaysMovingAverage;
        }

        public String getTwoHundredDaysMovingAverage() {
            return TwoHundredDaysMovingAverage;
        }

        public void setTwoHundredDaysMovingAverage(String twoHundredDaysMovingAverage) {
            TwoHundredDaysMovingAverage = twoHundredDaysMovingAverage;
        }

        public String getChangeFromTwoHundredDaysMovingAverage() {
            return ChangeFromTwoHundredDaysMovingAverage;
        }

        public void setChangeFromTwoHundredDaysMovingAverage(String changeFromTwoHundredDaysMovingAverage) {
            ChangeFromTwoHundredDaysMovingAverage = changeFromTwoHundredDaysMovingAverage;
        }

        public String getPercentChangeFromTwoHundredDaysMovingAverage() {
            return PercentChangeFromTwoHundredDaysMovingAverage;
        }

        public void setPercentChangeFromTwoHundredDaysMovingAverage(String percentChangeFromTwoHundredDaysMovingAverage) {
            PercentChangeFromTwoHundredDaysMovingAverage = percentChangeFromTwoHundredDaysMovingAverage;
        }

        public String getChangeFromFiftyDaysMovingAverage() {
            return ChangeFromFiftyDaysMovingAverage;
        }

        public void setChangeFromFiftyDaysMovingAverage(String changeFromFiftyDaysMovingAverage) {
            ChangeFromFiftyDaysMovingAverage = changeFromFiftyDaysMovingAverage;
        }

        public String getPercentChangeFromFiftyDaysMovingAverage() {
            return PercentChangeFromFiftyDaysMovingAverage;
        }

        public void setPercentChangeFromFiftyDaysMovingAverage(String percentChangeFromFiftyDaysMovingAverage) {
            PercentChangeFromFiftyDaysMovingAverage = percentChangeFromFiftyDaysMovingAverage;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
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

        public String getPricePaid() {
            return PricePaid;
        }

        public void setPricePaid(String pricePaid) {
            PricePaid = pricePaid;
        }

        public String getChangePercent() {
            return ChangePercent;
        }

        public void setChangePercent(String changePercent) {
            ChangePercent = changePercent;
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

        public String getExDividendDate() {
            return ExDividendDate;
        }

        public void setExDividendDate(String exDividendDate) {
            ExDividendDate = exDividendDate;
        }

        public String getPERatio() {
            return PERatio;
        }

        public void setPERatio(String PERatio) {
            this.PERatio = PERatio;
        }

        public String getDividendPayDate() {
            return DividendPayDate;
        }

        public void setDividendPayDate(String dividendPayDate) {
            DividendPayDate = dividendPayDate;
        }

        public String getPERatioRealtime() {
            return PERatioRealtime;
        }

        public void setPERatioRealtime(String PERatioRealtime) {
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

        public String getSharesOwned() {
            return SharesOwned;
        }

        public void setSharesOwned(String sharesOwned) {
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

        public String getTickerTrend() {
            return TickerTrend;
        }

        public void setTickerTrend(String tickerTrend) {
            TickerTrend = tickerTrend;
        }

        public String getOneYearTargetPrice() {
            return OneYearTargetPrice;
        }

        public void setOneYearTargetPrice(String oneYearTargetPrice) {
            OneYearTargetPrice = oneYearTargetPrice;
        }

        public String getVolume() {
            return Volume;
        }

        public void setVolume(String volume) {
            Volume = volume;
        }

        public String getHoldingsValue() {
            return HoldingsValue;
        }

        public void setHoldingsValue(String holdingsValue) {
            HoldingsValue = holdingsValue;
        }

        public String getHoldingsValueRealtime() {
            return HoldingsValueRealtime;
        }

        public void setHoldingsValueRealtime(String holdingsValueRealtime) {
            HoldingsValueRealtime = holdingsValueRealtime;
        }

        public String getYearRange() {
            return YearRange;
        }

        public void setYearRange(String yearRange) {
            YearRange = yearRange;
        }

        public String getDaysValueChange() {
            return DaysValueChange;
        }

        public void setDaysValueChange(String daysValueChange) {
            DaysValueChange = daysValueChange;
        }

        public String getDaysValueChangeRealtime() {
            return DaysValueChangeRealtime;
        }

        public void setDaysValueChangeRealtime(String daysValueChangeRealtime) {
            DaysValueChangeRealtime = daysValueChangeRealtime;
        }

        public String getStockExchange() {
            return StockExchange;
        }

        public void setStockExchange(String stockExchange) {
            StockExchange = stockExchange;
        }

        public String getDividendYield() {
            return DividendYield;
        }

        public void setDividendYield(String dividendYield) {
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

    public class Query {

        public Integer count;
        public String created;
        public String lang;
        public Results results;

    }

    public class Results {

        public List<SingleQuote> quote = new ArrayList<>();

        public List<SingleQuote> getQuote() {
            return quote;
        }
    }

}
