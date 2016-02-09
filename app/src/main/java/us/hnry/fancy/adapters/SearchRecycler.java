package us.hnry.fancy.adapters;

import android.app.ProgressDialog;
import android.content.Context;
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
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.StockService.ONESAPI;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;
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

                        final ProgressDialog fetchingProgress = new ProgressDialog(caller.getContext());
                        fetchingProgress.setTitle("Fetching your data");
                        fetchingProgress.setMessage("We're almost there!");
                        fetchingProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        fetchingProgress.setIndeterminate(true);
                        fetchingProgress.setCancelable(false);
                        fetchingProgress.show();

                        final String BASE_URL = BuildConfig.BASE_API_URL;
                        final String ENV = BuildConfig.ENV;
                        final String FORMAT = "json";

                        Symbol symbol = mResults.get(viewType);
                        QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbol.getSymbol());
                        String builtQuery = queryBuilder.build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ONESAPI onesapi = retrofit.create(ONESAPI.class);
                        Call<Single> call = onesapi.getQuotes(builtQuery, ENV, FORMAT);
                        call.enqueue(new Callback<Single>() {
                            @Override
                            public void onResponse(Response<Single> response) {
                                try {
                                    Quote.SingleQuote quote = response.body().query.results.getQuote();
                                    Intent launchDetail = new Intent(caller.getContext(), DetailActivity.class);
                                    launchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                    caller.getContext().startActivity(launchDetail);
                                } catch (NullPointerException e) {
                                    Log.v("Catch", "Reached.");
                                    Toast toast = null;
                                    if (response.code() == 401) {
                                        toast = Toast.makeText(caller.getContext(), "Unauthenticated", Toast.LENGTH_SHORT);
                                    } else if (response.code() >= 400) {
                                        toast = Toast.makeText(caller.getContext(), "Client error "
                                                + response.code() + " " + response.message(), Toast.LENGTH_SHORT);
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

        public interface ThorViewHolderClicks {
            void onItemClick(View caller);
        }
    }
}
