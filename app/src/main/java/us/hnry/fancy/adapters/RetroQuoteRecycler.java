package us.hnry.fancy.adapters;

import android.content.Intent;
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
 *
 */
public class RetroQuoteRecycler extends RecyclerView.Adapter<RetroQuoteRecycler.RetroQuoteViewHolder> {

    private ArrayList<Quote.SingleQuote> mResults;

    public RetroQuoteRecycler(ArrayList<Quote.SingleQuote> results) {
        this.mResults = results;
    }

    @Override
    public int getItemViewType(int position) {
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
        holder.closeTextView.setText(singleQuote.getPreviousClose());
        holder.openTextView.setText(singleQuote.getOpen());
        holder.askTextView.setText(singleQuote.getAsk());
        holder.itemView.setTag(singleQuote);
    }

    @Override
    public int getItemCount() {
        return mResults != null ? mResults.size() : 0;
    }

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

        public interface RetroQuoteViewHolderClicks{
            void OnItemClick(View caller);
        }
    }
}
