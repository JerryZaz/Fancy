package us.hnry.fancy.utils;

import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import us.hnry.fancy.network.model.SingleQuote;

/**
 * Created by Henry on 2/1/2016.
 * Constants' holder and Support methods used app-wide to validate,
 * format, set-up the information.
 */
public class Utility {
    // The detail view only opens QUOTE_INTENT intents.
    public static final String QUOTE_INTENT = "intent_parcelable_quote";
    public static final String SEARCH_INTENT = "intent_search_type_selector";

    public static final int THOR_SEARCH = 0;
    public static final int SYMBOL_SEARCH = 1;

    public static final double DEFAULT_DOUBLE = -1.23;

    /**
     * Used by the Main adapter to determine what color to use on the current ask.
     *
     * @param quote being prepared.
     * @return positive if the quote went up, negative if it went down; default double if NaN
     */
    public static double compareAskOpen(@NonNull SingleQuote quote) {
        try {
            double currentAsk = Double.parseDouble(quote.getAsk());
            double open = Double.parseDouble(quote.getOpen());
            return currentAsk - open;
        } catch (NumberFormatException e) {
            return Utility.DEFAULT_DOUBLE;
        }
    }

    /**
     * Gives a printable format to a float number.
     *
     * @param original number to format
     * @return Formatted version for printing, or "N/A".
     */
    public static String formatDouble(double original) {
        if (original == DEFAULT_DOUBLE) return "N/A";
        if (original > 999999)
            return String.format(Locale.US, "%#,.2f", (original / 1000000)) + "M";
        return String.format(Locale.US, "%#,.2f", original);
    }

    /**
     * Takes a String considering it may be holding an unformatted float and gives format to it.
     *
     * @param original number to format
     * @return Formatted version for printing, or "N/A".
     */
    public static String formatDouble(String original)
            throws NumberFormatException {
        if (original != null) {
            double fetched = Double.parseDouble(original);
            if (fetched == -1.23) return "N/A";
            if (fetched > 999999)
                return String.format(Locale.US, "%#,.2f", (fetched / 1000000)) + "M";
            return String.format(Locale.US, "%#,.2f", fetched);
        }
        return "N/A";
    }

    /**
     * Support method for Thor search that doesn't react very well to 2+ words requests
     *
     * @param original String to be cropped.
     * @return the query, cropped if necessary.
     */
    public static String getStringBeforeBlank(String original) {
        try {
            int indexOfBlank = original.indexOf(" ");
            return original.substring(0, indexOfBlank);
        } catch (StringIndexOutOfBoundsException e) {
            return original;
        }
    }

    /**
     * Method used to give a pleasant formatting to the keys in the detail view and the
     * package put in a Share intent.
     *
     * @param camelCase original text
     * @return fancy String representation of the key of the HashMap sent to the detail view and
     * is also put into a Share intent.
     */
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

    /**
     * Simple method to remove xml tags left over in the JSON when received from the server.
     * Made to fit, don't use it anywhere else.
     *
     * @param in String with xml brackets
     * @return String without xml brackets
     */
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

    /**
     * Takes a SingleQuote object and makes a Map with the name of the method and
     * its corresponding instance value. Then it takes the keys and values and arranges
     * them in a printable String that's assembled when the user taps the Share button.
     *
     * @param quote the SingleQuote to be consumed
     * @return Printable String representation of the SingleQuote
     */
    public static String consumeParcelableQuote(SingleQuote quote) throws InvocationTargetException, IllegalAccessException {
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
            if (!value.equals("null")) {
                try {
                    value = Utility.formatDouble(value);
                } catch (NumberFormatException ignored) {

                }
                builder.append(Utility.splitCamelCase(key)).append(": ").append(value).append("\n");
            }
        }
        return builder.toString();
    }
}