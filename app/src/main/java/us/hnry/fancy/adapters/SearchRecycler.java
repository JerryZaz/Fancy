package us.hnry.fancy.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Remastered SearchAdapter to handle Recycler View and its Click events.
 * Implements RecyclerView Adapter and RecyclerView ViewHolder
 */
public class SearchRecycler extends RecyclerView.Adapter<SearchRecycler.SearchRecyclerViewHolder> {

    private ArrayList<Symbol> mResults;

    /**
     * Adapter constructor.
     * @param param The results of a Thor Search.
     */
    public SearchRecycler(ArrayList<Symbol> param) {
        this.mResults = param;
    }

    @Override
    public int getItemViewType(int position) {
        // Work-around to get the item from the list of results in the onClick event
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

                        final ProgressDialog fetchingProgress = new ProgressDialog(caller.getContext());
                        fetchingProgress.setTitle("Fetching your data");
                        fetchingProgress.setMessage("We're almost there!");
                        fetchingProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        fetchingProgress.setIndeterminate(true);
                        fetchingProgress.setCancelable(false);
                        fetchingProgress.show();

                        final String ENV = BuildConfig.ENV;
                        final String FORMAT = "json";

                        //Apply the work-around to get a single ite,
                        Symbol symbol = mResults.get(viewType);

                        //Call the Utility QuoteQueryBuilder class
                        // to build a query with the result symbol
                        QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbol.getSymbol());
                        String builtQuery = queryBuilder.build();

                        //Call to the service to make an HTTP request to the server
                        Call<Single> call = StockService.Implementation.get(BuildConfig.BASE_API_URL)
                                .getSingleQuote(builtQuery, ENV, FORMAT);

                        // Execute the request asynchronously with a callback listener to fetch the
                        // response or the error message (if any) while talking to the server,
                        // creating the request, or processing the response.
                        call.enqueue(new Callback<Single>() {

                            /**
                             * From the interface: Invoked for a received HTTP response.
                             * @param response call .isSuccess to determine if the response indicates
                             *                 success.
                             */
                            @Override
                            public void onResponse(Response<Single> response) {
                                try {
                                    // Dig into the response, which holds an instance of the Single
                                    // model class, to fetch the actual Quote.
                                    Quote.SingleQuote quote = response.body().query.results
                                            .getQuote();

                                    Intent launchDetail
                                            = new Intent(caller.getContext(), DetailActivity.class);
                                    launchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                    caller.getContext().startActivity(launchDetail);

                                } catch (NullPointerException e) {
                                    Log.v("Catch", "Reached.");
                                    Toast toast = null;
                                    if (response.code() == 401) {
                                        toast = Toast.makeText(caller.getContext(),
                                                "Unauthenticated", Toast.LENGTH_SHORT);
                                    } else if (response.code() >= 400) {
                                        toast = Toast.makeText(caller.getContext(), "Client error "
                                                + response.code() + " " + response.message(),
                                                Toast.LENGTH_SHORT);
                                    }
                                    if (toast != null) {
                                        toast.show();
                                    }
                                } finally {
                                    fetchingProgress.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("getQuotes threw ", t.getMessage());
                                fetchingProgress.dismiss();
                            }
                        });

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

    /**
     * Helper method that updates the data displayed in the recycler view
     * @param param the new information to be displayed
     */
    public void swapList(ArrayList<Symbol> param) {
        if (mResults != null) {
            mResults.clear();
            mResults.addAll(param);
        } else {
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

        /**
         * Interface to define the OnClick events of the recycler view
         */
        public interface ThorViewHolderClicks {
            void onItemClick(View caller);
        }
    }
}
