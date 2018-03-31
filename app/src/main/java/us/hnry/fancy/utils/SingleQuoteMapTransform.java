package us.hnry.fancy.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Function;
import us.hnry.fancy.network.model.SingleQuote;

/**
 * @author Henry
 *         10/29/2017
 */

public class SingleQuoteMapTransform implements Function<SingleQuote, Map<String, String>> {

    @Override
    public Map<String, String> apply(SingleQuote singleQuote) throws Exception {
        Method[] methods = singleQuote.getClass().getMethods();
        Map<String, String> map = new HashMap<>();

        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String name = m.getName().substring(3);
                if (!"Class".equals(name)) {
                    String value = String.valueOf(m.invoke(singleQuote));
                    if (!"null".equals(value)) {
                        if (!"LastTradeWithTime".equals(name)) {
                            map.put(name, value);
                        } else {
                            value = Utility.removeXMLTagsFromLastTradeWithTime(value);
                            map.put(name, value);
                        }
                    }
                }
            }
        }
        return map;
    }
}