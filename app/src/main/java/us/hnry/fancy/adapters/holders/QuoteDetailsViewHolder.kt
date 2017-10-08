package us.hnry.fancy.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import us.hnry.fancy.R

/**
 * @author Henry
 * 10/7/2017
 */
class QuoteDetailsViewHolder(itemView: View, private val listener: DetailViewHolderClicks) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val keyTextView: TextView = itemView.findViewById(R.id.detail_single_item_key)
    private val valueTextView: TextView = itemView.findViewById(R.id.detail_single_item_value)

    init {
        itemView.setOnClickListener(this)
    }

    fun setKeyText(keyText: String) {
        keyTextView.text = keyText
    }

    fun setValueText(valueText: String) {
        valueTextView.text = valueText
    }

    override fun onClick(v: View) {
        listener.onItemClick(v)
    }

    interface DetailViewHolderClicks {
        fun onItemClick(caller: View)
    }
}