package us.hnry.fancy.adapters.holders

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author Henry
 * 10/21/2017
 */
open class BaseViewHolder<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: B? = null
}