package us.hnry.fancy.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import us.hnry.fancy.Core
import us.hnry.fancy.R
import us.hnry.fancy.adapters.StockDetailAdapter
import us.hnry.fancy.presentation.StockDetailPresenterImpl
import us.hnry.fancy.presentation.view.StockDetailView
import us.hnry.fancy.presentation.wrapper.StockDetailRowItem
import us.hnry.fancy.ui.DividerItemDecoration
import us.hnry.fancy.utils.Utility
import javax.inject.Inject

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailFragment : Fragment(), StockDetailView {
    @Inject
    lateinit var presenter: StockDetailPresenterImpl

    val adapter: StockDetailAdapter by lazy { StockDetailAdapter(activity) }

    private var trackedFab: FloatingActionButton? = null
    var shareFab: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Core.stockDetailComponent.inject(this)
        attachPresenter()

        val symbol = activity.intent.getStringExtra(Utility.QUOTE_INTENT)
        if (symbol is String) {
            presenter.loadSymbolData(symbol)
        } else {
            detachPresenter()
            activity.finish()
        }

        val layout: View = inflater!!.inflate(R.layout.fragment_detail, container, false)

        val recyclerView: RecyclerView = layout.findViewById(R.id.content_detail_list_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity, null))
        recyclerView.adapter = adapter

        trackedFab = activity.findViewById(R.id.fab)
        trackedFab?.setOnClickListener { presenter.trackButtonTapped() }

        return layout
    }

    override fun attachPresenter() {
        presenter.attachView(this)
    }

    override fun detachPresenter() {
        presenter.detachView()
    }

    override fun updateHeader(title: String) {
        activity.title = title
    }

    override fun loadStockDetails(details: List<StockDetailRowItem>) {
        adapter.swapList(details)
    }

    override fun onSymbolAdded() {
        trackedFab?.setImageResource(R.drawable.ic_check_circle_white_24dp)
    }

    override fun onSymbolRemoved() {
        trackedFab?.setImageResource(R.drawable.ic_favorite_white_24dp)
    }

    override fun showErrorMessage(string: String) {
        Log.v(StockDetailFragment::class.java.simpleName, string)
    }
}