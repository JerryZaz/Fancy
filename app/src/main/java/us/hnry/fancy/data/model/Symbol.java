package us.hnry.fancy.data.model;

/**
 * Created by Henry on 1/31/2016.
 * Remastered on 16/02/08
 */
public class Symbol {

    public static final String SYMBOL = "symbol";
    public static final String TAG = "company";
    private String company;
    private String symbol;

    public Symbol(String company, String symbol) {
        this.company = company;
        this.symbol = symbol;
    }

    /**
     * @return The company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return The symbol
     */
    public String getSymbol() {
        if (symbol.length() > 4) {
            symbol = symbol.substring(0, 4);
        }
        return symbol.toUpperCase();
    }

    /**
     * @param symbol The symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
