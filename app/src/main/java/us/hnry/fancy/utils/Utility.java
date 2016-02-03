package us.hnry.fancy.utils;

import java.util.Set;

/**
 * Created by Henry on 2/1/2016.
 */
public class Utility {

    public static final String PERSISTENT = "savedData";
    public static final String PERSISTENT_SYMBOLS_SET = "key.symbols.set";
    public static final String[] DEFAULT_SYMBOLS = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "FB",
            "TSLA", "T", "TMUS", "YHOO", "NFLX"};
    public static final String STOCK_INTENT = "intent_parcelable_stock";
    public static final double DEFAULT_DOUBLE = -1.23;
    public static final long DEFAULT_LONG = -1;

    public static String[] getSymbols(Set<String> fromSharedPreferences) {
        return fromSharedPreferences.toArray(new String[fromSharedPreferences.size()]);
    }

    public static String formatDouble(double original) {
        if (original == DEFAULT_DOUBLE) return "N/A";
        return String.format("%#,.2f", original);
    }

    public static String getStringBeforeBlank(String original){
        try {
            int indexOfBlank = original.indexOf(" ");
            return original.substring(0, indexOfBlank);
        } catch (StringIndexOutOfBoundsException e){
            return original;
        }
    }
}
