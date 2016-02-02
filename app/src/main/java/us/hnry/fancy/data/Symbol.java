package us.hnry.fancy.data;

/**
 * Created by Henry on 1/31/2016.
 *
 */
public class Symbol {
    public static final String SYMBOL = "symbol";
    public static final String TAG = "company";
    private String mSymbol;
    private String mNameTag;

    public Symbol(String symbol, String tag){
        mSymbol = symbol;
        mNameTag = tag;
    }

    public String getSymbol() {
        if(mSymbol.length() > 4){
            mSymbol = mSymbol.substring(0,4);
        }
        return mSymbol.toUpperCase();
    }

    public String getNameTag() {
        return mNameTag;
    }
}
