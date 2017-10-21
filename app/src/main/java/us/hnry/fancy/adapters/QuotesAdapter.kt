package us.hnry.fancy.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.activity.DetailActivity
import us.hnry.fancy.adapters.holders.QuotesViewHolder
import us.hnry.fancy.fragments.PersistentSymbolsChangedListener
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.network.model.Symbol
import us.hnry.fancy.ui.ItemTouchHelperListener
import us.hnry.fancy.utils.SymbolsHelper
import us.hnry.fancy.utils.Utility

/**
 * @author Henry
 * 10/20/2017
 */
class QuotesAdapter(context: Context, listener: PersistentSymbolsChangedListener) : BaseAdapter<SingleQuote, QuotesViewHolder>(context), QuotesViewHolder.RetroQuoteViewHolderClicks, ItemTouchHelperListener {
    private val symbolsHelper: SymbolsHelper by lazy { SymbolsHelper(context, listener) }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.single_row_main_card, parent, false)
        return QuotesViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder?, position: Int) {
        val quote = items[position]
        holder?.let {
            holder.setSymbol(quote.symbol)
            holder.setSymbolName(quote.name)
            holder.setCloseValue(quote.previousClose)
            holder.setOpenValue(Utility.formatDouble(quote.open))
            holder.setAskValue(Utility.formatDouble(quote.ask))

            if (quote.open != null && quote.ask != null) {
                val difference = Utility.compareAskOpen(quote)
                holder.askTextView.setTextColor(
                        when {
                            difference > 0 -> ContextCompat.getColor(context, R.color.currentAskGreen)
                            difference < 0 -> ContextCompat.getColor(context, R.color.currentAskRed)
                            else -> ContextCompat.getColor(context, R.color.currentAskNeutral)
                        }
                )
            }
            holder.itemView.tag = quote
        }
    }

    override fun onItemSwiped(adapterPosition: Int?) {
        adapterPosition?.let {
            val quote = items[adapterPosition]
            items.remove(quote)
            symbolsHelper.removeSymbol(Symbol(quote.name, quote.symbol))
            notifyDataSetChanged()
        }
    }

    override fun onItemDragged() {
        // nothing to do here
    }

    override fun onItemClick(view: View) {
        val launchDetailIntent = Intent(view.context, DetailActivity::class.java)
        launchDetailIntent.putExtra(Utility.QUOTE_INTENT, view.tag as SingleQuote)
        context.startActivity(launchDetailIntent)
    }
}