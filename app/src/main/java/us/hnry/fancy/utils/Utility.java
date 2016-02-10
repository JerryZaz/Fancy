package us.hnry.fancy.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import us.hnry.fancy.deprecated.Stock;
import us.hnry.fancy.models.Quote;

/**
 * Created by Henry on 2/1/2016.
 */
public class Utility {

    public static final String PERSISTENT = "savedData";
    public static final String PERSISTENT_SYMBOLS_SET = "key.symbols.set";

    public static final String STOCK_INTENT = "intent_parcelable_stock";
    public static final String QUOTE_INTENT = "intent_parcelable_quote";
    public static final String SEARCH_INTENT = "intent_search_type_selector";

    public static final String[] DEFAULT_SYMBOLS = new String[]{"GOOG", "MSFT", "AAPL", "AMZN",
            "FB", "TSLA", "T", "TMUS", "YHOO", "NFLX"};
    public static final double DEFAULT_DOUBLE = -1.23;
    public static final long DEFAULT_LONG = -1;
    public static final int THOR_SEARCH = 0;
    public static final int SYMBOL_SEARCH = 1;

    public static String[] getSymbols(Set<String> fromSharedPreferences) {
        return fromSharedPreferences.toArray(new String[fromSharedPreferences.size()]);
    }

    public static double compareAskOpen(Quote.SingleQuote quote) {
        try {
            double currentAsk = Double.parseDouble(quote.getAsk());
            double open = Double.parseDouble(quote.getOpen());
            return currentAsk - open;
        } catch (NumberFormatException e) {
            return Utility.DEFAULT_DOUBLE;
        }
    }

    public static String formatDouble(double original) {
        if (original == DEFAULT_DOUBLE) return "N/A";
        if (original > 999999) return String.format("%#,.2f", (original / 1000000)) + "M";
        return String.format("%#,.2f", original);
    }

    public static String formatDouble(String original)
            throws NumberFormatException {
        if (original != null) {
            double fetched = Double.parseDouble(original);
            if (fetched == -1.23) return "N/A";
            if (fetched > 999999) return String.format("%#,.2f", (fetched / 1000000)) + "M";
            return String.format("%#,.2f", fetched);
        }
        return "N/A";
    }

    public static String getStringBeforeBlank(String original) {
        try {
            int indexOfBlank = original.indexOf(" ");
            return original.substring(0, indexOfBlank);
        } catch (StringIndexOutOfBoundsException e) {
            return original;
        }
    }

    public static String splitCamelCase(String camelCase) {
        char[] chars = camelCase.toCharArray();
        StringBuilder builder = new StringBuilder(String.valueOf(Character.toUpperCase(chars[0])));
        for (int i = 1; i < chars.length; i++) {
            char previousChar = chars[i - 1];
            char singleChar = chars[i];
            if (Character.isUpperCase(singleChar)) {
                if (!Character.isUpperCase(previousChar)) {
                    builder.append(" ").append(String.valueOf(singleChar));
                } else {
                    if (i < chars.length - 2) {
                        //if it's followed by a lowercase, add space
                        char nextChar = chars[i + 1];
                        if (!Character.isUpperCase(nextChar)) {
                            builder.append(" ").append(String.valueOf(singleChar));
                        } else {
                            builder.append(String.valueOf(singleChar));
                        }
                    } else {
                        builder.append(String.valueOf(singleChar));
                    }
                }
            } else {
                builder.append(String.valueOf(singleChar));
            }
        }
        return builder.toString();
    }

    public static String removeXMLTagsFromLastTradeWithTime(String in) {
        String symbols = "</b>";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < in.length(); i++) {
            String temp = in.substring(i, i + 1);
            if (!symbols.contains(temp)) {
                builder.append(temp);
            } else {
                builder.append("");
            }
        }
        return builder.toString();
    }

    @Deprecated
    public static String consumeParcelableStock(Stock stock)
            throws InvocationTargetException, IllegalAccessException {

        ArrayList<String> keys = new ArrayList<>();
        Method[] methods = stock.getClass().getMethods();
        Map<String, String> map = new HashMap<>();

        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String value = String.valueOf(m.invoke(stock));
                String name = m.getName().substring(3);
                if (!name.equals("Class")) {
                    map.put(name, value);
                    keys.add(name);
                }
            }
        }

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = splitCamelCase(entry.getKey());
            String value;
            try {
                value = formatDouble(entry.getValue());
            } catch (NumberFormatException e) {
                value = entry.getValue();
            }
            builder.append(key).append(": ").append(value).append("\n");
        }

        return builder.toString();
    }

    public static String consumeParcelableQuote(Quote.SingleQuote quote)
            throws InvocationTargetException, IllegalAccessException {
        ArrayList<String> keys = new ArrayList<>();
        Method[] methods = quote.getClass().getMethods();
        Map<String, String> map = new HashMap<>();

        for (Method m : methods) {
            if (m.getName().startsWith("get")) {

                String name = m.getName().substring(3);
                if (!name.equals("Class")) {
                    String value = String.valueOf(m.invoke(quote));
                    map.put(name, value);
                    keys.add(name);
                }
            }
        }

        Collections.sort(keys);

        StringBuilder builder = new StringBuilder();

        for (String key : keys) {
            String value;
            if (!key.equals("LastTradeWithTime")) {
                value = map.get(key);
            } else {
                value = Utility.removeXMLTagsFromLastTradeWithTime(map.get(key));
            }
            if(!value.equals("null")) {
                try{
                    value = Utility.formatDouble(value);
                } catch (NumberFormatException e){

                }
                builder.append(Utility.splitCamelCase(key)).append(": ").append(value).append("\n");
            }
        }
        return builder.toString();
    }
}
