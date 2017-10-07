package us.hnry.fancy.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import us.hnry.fancy.R;

/**
 * @author Henry
 *         10/6/2017
 */

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView symbolTextView;
    private TextView companyTextView;
    private ThorViewHolderClicks mListener;

    public SearchViewHolder(View itemView, ThorViewHolderClicks listener) {
        super(itemView);
        symbolTextView = itemView.findViewById(R.id.search_single_row_symbol);
        companyTextView = itemView.findViewById(R.id.search_single_row_company);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    public TextView getSymbolTextView() {
        return symbolTextView;
    }

    public TextView getCompanyTextView() {
        return companyTextView;
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(v);
    }

    /**
     * Interface to define the OnClick events of the recycler view
     */
    public interface ThorViewHolderClicks {
        void onItemClick(View caller);
    }
}
