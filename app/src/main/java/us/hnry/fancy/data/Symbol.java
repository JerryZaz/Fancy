package us.hnry.fancy.data;

/**
 * Created by Henry on 1/31/2016.
 *
 */
public class Symbol {
    private String mSymbol;
    private String mNameTag;

    public String getSymbol() {
        if(mSymbol.length() > 4){
            mSymbol = mSymbol.substring(0,4);
        }
        return mSymbol.toUpperCase();
    }

    public String getNameTag() {
        return mNameTag;
    }

    public Symbol(String symbol, String tag){
        mSymbol = symbol;
        mNameTag = tag;
    }
}
