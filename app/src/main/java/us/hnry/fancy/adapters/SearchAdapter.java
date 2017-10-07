package us.hnry.fancy.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.holders.SearchViewHolder;
import us.hnry.fancy.network.StockService;
import us.hnry.fancy.network.model.Quote;
import us.hnry.fancy.network.model.SingleQuote;
import us.hnry.fancy.network.model.Symbol;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Remastered SearchAdapter to handle Recycler View and its Click events.
 * Implements RecyclerView Adapter and RecyclerView ViewHolder
 */
public class SearchAdapter extends BaseAdapter<Symbol, SearchViewHolder> {

    public SearchAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_search, parent, false);

        return new SearchViewHolder(
                itemView,
                caller -> {

                    final ProgressDialog fetchingProgress = new ProgressDialog(caller.getContext());
                    fetchingProgress.setTitle("Fetching your data");
                    fetchingProgress.setMessage("We're almost there!");
                    fetchingProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    fetchingProgress.setIndeterminate(true);
                    fetchingProgress.setCancelable(false);
                    fetchingProgress.show();

                    final String ENV = BuildConfig.ENV;
                    final String FORMAT = "json";

                    Symbol symbol = (Symbol) itemView.getTag();

                    //Call the Utility QuoteQueryBuilder class
                    // to build a query with the result symbol
                    QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(symbol.getSymbol());
                    String builtQuery = queryBuilder.build();

                    //Call to the service to make an HTTP request to the server
                    Call<Quote<SingleQuote>> call = StockService.Implementation.get(BuildConfig.BASE_API_URL)
                            .getSingleQuote(builtQuery, ENV, FORMAT);

                    // Execute the request asynchronously with a callback listener to fetch the
                    // response or the error message (if any) while talking to the server,
                    // creating the request, or processing the response.
                    call.enqueue(new Callback<Quote<SingleQuote>>() {
                        /**
                         * From the interface: Invoked for a received HTTP response.
                         *
                         * @param response call .isSuccess to determine if the response indicates
                         *                 success.
                         */
                        @Override
                        public void onResponse(Call<Quote<SingleQuote>> call, Response<Quote<SingleQuote>> response) {
                            if (response != null) {
                                Quote<SingleQuote> single = response.body();
                                if (single != null && single.getQuery() != null && single.getQuery().getCount() > 0) {
                                    // Dig into the response, which holds an instance of the Single
                                    // model class, to fetch the actual Quote.

                                    SingleQuote quote = single.getQuery().getResults().getQuote();

                                    Intent launchDetail
                                            = new Intent(caller.getContext(), DetailActivity.class);
                                    launchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                    caller.getContext().startActivity(launchDetail);
                                } else {
                                    if (response.code() == 401) {
                                        Toast.makeText(caller.getContext(),
                                                "Unauthenticated", Toast.LENGTH_SHORT).show();
                                    } else if (response.code() >= 400) {
                                        Toast.makeText(caller.getContext(), "Client error "
                                                        + response.code() + " " + response.message(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            fetchingProgress.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Quote<SingleQuote>> call, Throwable t) {
                            Log.e("getQuotes threw ", t.getMessage());
                            fetchingProgress.dismiss();
                        }
                    });

                }
        );
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Symbol symbol = getItems().get(position);
        holder.getSymbolTextView().setText(symbol.getSymbol());
        holder.getCompanyTextView().setText(symbol.getCompany());
        holder.itemView.setTag(symbol);
    }
}
