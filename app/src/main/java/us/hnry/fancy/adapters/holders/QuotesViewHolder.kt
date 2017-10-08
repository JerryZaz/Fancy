package us.hnry.fancy.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import us.hnry.fancy.R

/**
 * @author Henry
 * 10/7/2017
 */
class QuotesViewHolder(itemView: View, private val listener: RetroQuoteViewHolderClicks) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val symbolTextView: TextView = itemView.findViewById(R.id.single_row_text_view_symbol)
    private val nameTextView: TextView = itemView.findViewById(R.id.single_row_text_view_name)
    private val closeTextView: TextView = itemView.findViewById(R.id.single_row_text_view_close)
    private val openTextView: TextView = itemView.findViewById(R.id.single_row_text_view_open)
    val askTextView: TextView = itemView.findViewById(R.id.single_row_text_view_ask)

    init {
        itemView.setOnClickListener(this)
    }

    fun setSymbol(symbol: String) {
        symbolTextView.text = symbol
    }

    fun setSymbolName(name: String) {
        nameTextView.text = name
    }

    fun setCloseValue(closeValue: String) {
        closeTextView.text = closeValue
    }

    fun setOpenValue(openValue: String) {
        openTextView.text = openValue
    }

    fun setAskValue(askValue: String) {
        askTextView.text = askValue
    }

    override fun onClick(v: View) {
        listener.onItemClick(v)
    }

    interface RetroQuoteViewHolderClicks {
        fun onItemClick(view: View)
    }
}