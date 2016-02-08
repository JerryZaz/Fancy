package us.hnry.fancy.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 2/7/2016.
 */
public class Quote {

    public Query query;

    public class Query {

        public Integer count;
        public String created;
        public String lang;
        public Results results;

    }

    public class Results {

        public List<SingleQuote> quote = new ArrayList<>();

    }

    public static class SingleQuote{
        public String symbol;
        public String Ask;
        public String AverageDailyVolume;

        public SingleQuote(String symbol, String ask, String averageDailyVolume) {
            this.symbol = symbol;
            Ask = ask;
            AverageDailyVolume = averageDailyVolume;
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
    }

}
