package us.hnry.fancy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Henry on 2/1/2016.
 * Hax to customize queries to the yahoo.finance.quotes database
 */
public class QuoteQueryBuilder {

    final String BASE_QUERY = "select * from yahoo.finance.quotes where symbol in ";
    private String[] symbols;

    public QuoteQueryBuilder(String... toQuery){
        symbols = toQuery;
    }

    public String build(){
        return BASE_QUERY + arrangeSymbols();
    }

    private String arrangeSymbols(){

        ArrayList<String> temp = new ArrayList<>(Arrays.asList(symbols));
        Collections.sort(temp);
        symbols = temp.toArray(new String[temp.size()]);

        StringBuilder builder = new StringBuilder("(");

        for(int i = 0; i < symbols.length; i++){
            builder.append("\"");
            builder.append(symbols[i]);
            builder.append("\"");
            if(i < symbols.length - 1){
                builder.append(",");
            }
        }
        builder.append(")");

        return builder.toString();
    }
}