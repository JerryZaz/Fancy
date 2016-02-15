package us.hnry.fancy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Remastered StockRecycler to handle RecyclerView and its click events.
 *
 * Name of the class also makes reference to the Retrofit implementation that fetches
 * the data onto the new Quote model class.
 */
public class RetroQuoteRecycler extends RecyclerView.Adapter<RetroQuoteRecycler.RetroQuoteViewHolder> {

    private ArrayList<Quote.SingleQuote> mResults;
    private Context mContext;

    /**
     * Constructor.
     * @param results ArrayList of SingleQuote objects
     * @param context the context.
     */
    public RetroQuoteRecycler(ArrayList<Quote.SingleQuote> results, Context context) {
        this.mResults = results;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        // work-around to get the item being clicked on
        return position;
    }

    @Override
    public RetroQuoteViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_main_card, parent, false);
        return new RetroQuoteViewHolder(itemView, new RetroQuoteViewHolder.RetroQuoteViewHolderClicks() {
            @Override
            public void OnItemClick(View caller) {
                Intent launchDetail = new Intent(caller.getContext(), DetailActivity.class);
                launchDetail.putExtra(Utility.QUOTE_INTENT, mResults.get(viewType));
                caller.getContext().startActivity(launchDetail);
            }
        });
    }

    @Override
    public void onBindViewHolder(RetroQuoteViewHolder holder, int position) {
        Quote.SingleQuote singleQuote = mResults.get(position);
        holder.symbolTextView.setText(singleQuote.getSymbol());
        holder.nameTextView.setText(singleQuote.getName());

        holder.closeTextView.setText(Utility.formatDouble(singleQuote.getPreviousClose()));

        String open = singleQuote.getOpen();
        holder.openTextView.setText(Utility.formatDouble(open));
        String ask = singleQuote.getAsk();
        holder.askTextView.setText(Utility.formatDouble(ask));
        if(open != null && ask != null){
            double difference = Utility.compareAskOpen(singleQuote);
            holder.askTextView.setTextColor(
                    difference > 0 ? mContext.getResources().getColor(R.color.currentAskGreen) :
                            difference < 0 ? Color.RED : Color.BLACK
            );
        }

        holder.itemView.setTag(singleQuote);
    }

    @Override
    public int getItemCount() {
        return mResults != null ? mResults.size() : 0;
    }

    /**
     * Helper method that updates the data displayed in the recycler view
     * @param param the new information to be displayed
     */
    public void swapList(ArrayList<Quote.SingleQuote> param) {
        if(mResults != null){
            mResults.clear();
            mResults.addAll(param);
        }
        else {
            mResults = param;
        }
        notifyDataSetChanged();
    }

    public static class RetroQuoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView symbolTextView;
        public TextView nameTextView;
        public TextView closeTextView;
        public TextView openTextView;
        public TextView askTextView;
        public RetroQuoteViewHolderClicks mListener;

        public RetroQuoteViewHolder(View itemView, RetroQuoteViewHolderClicks listener) {
            super(itemView);
            symbolTextView = (TextView) itemView.findViewById(R.id.single_row_text_view_symbol);
            nameTextView = (TextView) itemView.findViewById(R.id.single_row_text_view_name);
            closeTextView = (TextView) itemView.findViewById(R.id.single_row_text_view_close);
            openTextView = (TextView) itemView.findViewById(R.id.single_row_text_view_open);
            askTextView = (TextView) itemView.findViewById(R.id.single_row_text_view_ask);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.OnItemClick(v);
        }

        /**
         * Interface to define the OnClick events of the recycler view
         */
        public interface RetroQuoteViewHolderClicks{
            void OnItemClick(View caller);
        }
    }
}
