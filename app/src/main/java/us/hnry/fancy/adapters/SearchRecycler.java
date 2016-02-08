package us.hnry.fancy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.FetchStockTask;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Remastered SearchAdapter to handle Recycler View
 */
public class SearchRecycler extends RecyclerView.Adapter<SearchRecycler.SearchRecyclerViewHolder> {

    private ArrayList<Symbol> mResults;
    private Context mContext;

    public SearchRecycler(ArrayList<Symbol> param, Context context) {
        this.mResults = param;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_search, parent, false);
        return new SearchRecyclerViewHolder(
                itemView,
                new SearchRecyclerViewHolder.ThorViewHolderClicks() {
                    @Override
                    public void onItemClick(final View caller) {
                        new Thread(){
                            @Override
                            public void run() {
                                Symbol symbol = mResults.get(viewType);
                                FetchStockTask task = new FetchStockTask(caller.getContext());
                                QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbol.getSymbol());
                                task.execute(queryBuilder.build());
                                try {
                                    ArrayList<Stock> results = task.get();
                                    if(results.size() > 0){
                                        Intent launchDetail = new Intent(caller.getContext(), DetailActivity.class);
                                        launchDetail.putExtra(Utility.STOCK_INTENT, results.get(0));
                                        caller.getContext().startActivity(launchDetail);
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }
        );
    }

    @Override
    public void onBindViewHolder(SearchRecyclerViewHolder holder, int position) {
        Symbol symbol = mResults.get(position);
        holder.symbolTextView.setText(symbol.getSymbol());
        holder.companyTextView.setText(symbol.getCompany());
        holder.itemView.setTag(symbol);
    }

    @Override
    public int getItemCount() {
        return mResults != null ? mResults.size() : 0;
    }

    public void swapList(ArrayList<Symbol> param) {
        if(mResults != null){
            mResults.clear();
            mResults.addAll(param);
        }
        else {
            mResults = param;
        }
        notifyDataSetChanged();
    }

    public static class SearchRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView symbolTextView;
        public TextView companyTextView;
        ThorViewHolderClicks mListener;

        public SearchRecyclerViewHolder(View itemView, ThorViewHolderClicks listener) {
            super(itemView);
            symbolTextView = (TextView) itemView.findViewById(R.id.search_single_row_symbol);
            companyTextView = (TextView) itemView.findViewById(R.id.search_single_row_company);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v);
        }

        public interface ThorViewHolderClicks{
            void onItemClick(View caller);
        }
    }
}
