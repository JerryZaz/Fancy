package us.hnry.fancy.utils

import us.hnry.fancy.BuildConfig

/**
 * @author Henry
 * 10/7/2017
 * Hax to assemble queries to the API.
 */
class QuoteQueryBuilder
/**
 * Initializes the array upon which the query will be built on.
 * @param symbols Not to be confused with objects of the Symbol class,
 *                this method takes a String representation of the symbol ("AMZN, "GOOG").
 */
(vararg var symbols: String) {

    /**
     * Assembles the query
     * @return query assembled
     */
    fun build(): String {
        return BuildConfig.BASE_QUERY + arrangeSymbols()
    }

    /**
     * The API takes its query in a very particular format that can't be manipulated, updated
     * dynamically, using regular HTTP annotations, using replacement blocks and parameters or
     * query items, as it's not in HTTP format. So, it must be previously built and passed on
     * as a query parameter as a single block.
     * Symbols come in as [AAAA,BBBB,CCCC] they leave as ("AAAA","BBBB","CCCC")
     * @return symbols inserted into the required structure.
     */
    fun arrangeSymbols(): String {
        val builder = StringBuilder()
        builder.append("(")

        symbols.sort()
        symbols.forEachIndexed { index, each ->
            val cased = each.toUpperCase()
            builder.append("\"$cased\"")
            if (index < symbols.size - 1) {
                builder.append(",")
            }
        }
        builder.append(")")
        return builder.toString()
    }
}