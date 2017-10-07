package us.hnry.fancy.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import us.hnry.fancy.R;

/**
 * @author Henry
 *         10/6/2017
 *         ViewHolder implementation for the RecyclerView on the main screen.
 */
public class QuotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView symbolTextView;
    private TextView nameTextView;
    private TextView closeTextView;
    private TextView openTextView;
    private TextView askTextView;
    private RetroQuoteViewHolderClicks mListener;

    /**
     * ViewHolder constructor for the RecyclerView on the main screen
     *
     * @param itemView Layout that contains the fields that need to be populated
     * @param listener Registers a listener for the click events
     */
    public QuotesViewHolder(View itemView, RetroQuoteViewHolderClicks listener) {
        super(itemView);
        symbolTextView = itemView.findViewById(R.id.single_row_text_view_symbol);
        nameTextView = itemView.findViewById(R.id.single_row_text_view_name);
        closeTextView = itemView.findViewById(R.id.single_row_text_view_close);
        openTextView = itemView.findViewById(R.id.single_row_text_view_open);
        askTextView = itemView.findViewById(R.id.single_row_text_view_ask);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.OnItemClick(v);
    }

    public void setSymbol(String symbol) {
        symbolTextView.setText(symbol);
    }

    public void setSymbolName(String name) {
        nameTextView.setText(name);
    }

    public void setCloseValue(String closeValue) {
        closeTextView.setText(closeValue);
    }

    public void setOpenValue(String openValue) {
        openTextView.setText(openValue);
    }

    public void setAskValue(String askValue) {
        askTextView.setText(askValue);
    }

    public TextView getAskTextView() {
        return askTextView;
    }

    /**
     * Interface to define the OnClick events of the recycler view
     */
    public interface RetroQuoteViewHolderClicks {
        void OnItemClick(View caller);
    }
}