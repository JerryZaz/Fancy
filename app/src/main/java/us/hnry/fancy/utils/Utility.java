package us.hnry.fancy.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import us.hnry.fancy.data.Stock;

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

    public static String formatDouble(String original)
            throws NumberFormatException {
        double fetched = Double.parseDouble(original);
        if (fetched == -1.23) return "N/A";
        return String.format("%#,.2f", fetched);
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
            char singleChar = chars[i];
            if (Character.isUpperCase(singleChar)) {
                builder.append(" ").append(String.valueOf(singleChar));
            } else {
                builder.append(String.valueOf(singleChar));
            }
        }
        return builder.toString();
    }

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
            } catch (NumberFormatException e){
                value = entry.getValue();
            }
            builder.append(key).append(": ").append(value).append("\n");
        }

        return builder.toString();
    }
}
