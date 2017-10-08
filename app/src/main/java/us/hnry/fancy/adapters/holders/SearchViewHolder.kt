package us.hnry.fancy.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import us.hnry.fancy.R

/**
 * @author Henry
 * 10/7/2017
 */
class SearchViewHolder(itemView: View, private val listener: ThorViewHolderClicks) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val symbolTextView: TextView = itemView.findViewById(R.id.search_single_row_symbol)
    val companyTextView: TextView = itemView.findViewById(R.id.search_single_row_company)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        listener.onItemClick(v)
    }

    /**
     * Interface to define the OnClick events of the recycler view
     */
    interface ThorViewHolderClicks {
        fun onItemClick(caller: View)
    }
}
