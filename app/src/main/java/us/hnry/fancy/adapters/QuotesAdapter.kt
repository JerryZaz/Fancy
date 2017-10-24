package us.hnry.fancy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.adapters.holders.QuotesViewHolder
import us.hnry.fancy.fragments.PersistentSymbolsChangedListener
import us.hnry.fancy.network.model.Symbol
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.ui.ItemTouchHelperListener
import us.hnry.fancy.utils.SymbolsHelper

/**
 * @author Henry
 * 10/20/2017
 */
class QuotesAdapter(context: Context, listener: PersistentSymbolsChangedListener) : BaseAdapter<StockDetail, QuotesViewHolder>(context), QuotesViewHolder.RetroQuoteViewHolderClicks, ItemTouchHelperListener {
    private val symbolsHelper: SymbolsHelper by lazy { SymbolsHelper(context, listener) }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.quote_detail_row, parent, false)
        return QuotesViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder?, position: Int) {
        val quote = items[position]
        holder?.let {
            holder.setData(quote)
            holder.itemView.tag = quote
        }
    }

    override fun onItemSwiped(adapterPosition: Int?) {
        adapterPosition?.let {
            val quote = items[adapterPosition]
            items.remove(quote)
            symbolsHelper.removeSymbol(Symbol(quote.symbolName, quote.symbol))
            notifyDataSetChanged()
        }
    }

    override fun onItemDragged() {
        // nothing to do here
    }

    override fun onItemClick(view: View) {
        // not implemented yet
    }
}