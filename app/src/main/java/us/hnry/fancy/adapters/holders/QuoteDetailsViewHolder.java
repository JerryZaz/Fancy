package us.hnry.fancy.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import us.hnry.fancy.R;

/**
 * @author Henry
 *         10/6/2017
 *         RecyclerView ViewHolder implementation to display each item of the Parcelable
 *         Quote object.
 */
public class QuoteDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView keyTextView;
    private TextView valueTextView;
    private DetailViewHolderClicks mListener;

    public QuoteDetailsViewHolder(View itemView, DetailViewHolderClicks listener) {
        super(itemView);
        keyTextView = itemView.findViewById(R.id.detail_single_item_key);
        valueTextView = itemView.findViewById(R.id.detail_single_item_value);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    public void setKeyText(String keyText) {
        keyTextView.setText(keyText);
    }

    public void setValueText(String valueText) {
        valueTextView.setText(valueText);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(v);
    }

    public interface DetailViewHolderClicks {
        void onItemClick(View caller);
    }
}
