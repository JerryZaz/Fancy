package us.hnry.fancy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import us.hnry.fancy.BuildConfig;

/**
 * Created by Henry on 2/1/2016.
 * Hax to assemble queries to the API.
 */
public class QuoteQueryBuilder {

    final String BASE_QUERY = BuildConfig.BASE_QUERY;
    private String[] symbols;

    /**
     * Initializes the array upon which the query will be built on.
     * @param toQuery Not to be confused with objects of the Symbol class,
     *                this method takes a String representation of the symbol ("AMZN, "GOOG").
     */
    public QuoteQueryBuilder(String... toQuery){
        symbols = toQuery;
    }

    /**
     * Assembles the query
     * @return query assembled
     */
    public String build(){
        return BASE_QUERY + arrangeSymbols();
    }

    /**
     * The API takes its query in a very particular format that can't be manipulated, updated
     * dynamically, using regular HTTP annotations, using replacement blocks and parameters or
     * query items, as it's not in HTTP format. So, it must be previously built and passed on
     * as a query parameter as a single block.
     * Symbols come in as [AAAA,BBBB,CCCC] they leave as ("AAAA","BBBB","CCCC")
     * @return symbols inserted into the required structure.
     */
    private String arrangeSymbols(){

        ArrayList<String> temp = new ArrayList<>(Arrays.asList(symbols));
        Collections.sort(temp);
        symbols = temp.toArray(new String[temp.size()]);

        StringBuilder builder = new StringBuilder("(");

        for(int i = 0; i < symbols.length; i++){
            builder.append("\"");
            builder.append(symbols[i].toUpperCase());
            builder.append("\"");
            if(i < symbols.length - 1){
                builder.append(",");
            }
        }
        builder.append(")");

        return builder.toString();
    }
}