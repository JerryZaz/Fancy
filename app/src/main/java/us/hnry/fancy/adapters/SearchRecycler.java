package us.hnry.fancy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import us.hnry.fancy.R;
import us.hnry.fancy.models.Symbol;

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
    public SearchRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_search, parent, false);
        SearchRecyclerViewHolder holder = new SearchRecyclerViewHolder(
                itemView,
                new SearchRecyclerViewHolder.ThorViewHolderClicks() {
                    @Override
                    public void onItemClick(View caller) {
                        Toast.makeText(parent.getContext(), "Unhandled click event", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return holder;
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
