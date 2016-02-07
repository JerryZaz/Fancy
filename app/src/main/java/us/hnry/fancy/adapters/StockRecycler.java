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
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/6/2016.
 *
 */
public class StockRecycler extends RecyclerView.Adapter<StockRecycler.StockRecyclerViewHolder> {

    private ArrayList<Stock> mQuotes;
    private Context mContext;

    public StockRecycler(ArrayList<Stock> quotes, Context context){
        mQuotes = quotes;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public StockRecyclerViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_main_card, parent, false);
        final StockRecyclerViewHolder holder = new StockRecyclerViewHolder(itemView, new StockRecyclerViewHolder.StockViewHolderClicks() {
            @Override
            public void onItemClick(View caller) {
                caller.getContext()
                        .startActivity(new Intent(caller.getContext(), DetailActivity.class)
                                .putExtra(Utility.STOCK_INTENT, mQuotes.get(viewType)));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(StockRecyclerViewHolder holder, int position) {
        Stock quote = mQuotes.get(position);
        holder.symbolTextView.setText(quote.getSymbol());
        holder.nameTextView.setText(quote.getName());
        holder.closeTextView.setText(Utility.formatDouble(quote.getPreviousClose()));
        holder.openTextView.setText(Utility.formatDouble(quote.getOpen()));
        holder.askTextView.setText(Utility.formatDouble(quote.getAsk()));

        double change = quote.getAsk() - quote.getOpen();
        holder.askTextView.setTextColor(
                change > 0 ? mContext.getResources().getColor(R.color.currentAskGreen) :
                        change < 0 ? Color.RED : Color.BLACK);

        holder.itemView.setTag(quote);
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    public static class StockRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView symbolTextView;
        public TextView nameTextView;
        public TextView closeTextView;
        public TextView openTextView;
        public TextView askTextView;
        public StockViewHolderClicks mListener;

        public StockRecyclerViewHolder(View itemView, StockViewHolderClicks listener) {
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
            mListener.onItemClick(v);
        }

        public interface StockViewHolderClicks{
            void onItemClick(View caller);
        }
    }
}
