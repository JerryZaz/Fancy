package us.hnry.fancy.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.hnry.fancy.R
import us.hnry.fancy.adapters.QuotesAdapter
import us.hnry.fancy.data.StockRepositoryImpl
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.presentation.StockPresenter
import us.hnry.fancy.presentation.StockPresenterImpl
import us.hnry.fancy.presentation.view.StockView
import us.hnry.fancy.ui.MainItemTouchCallback
import java.util.concurrent.Executors

/**
 * @author Henry
 * 10/8/2017
 */
class MainFragment : Fragment(), StockView {
    private val defaultSymbols = arrayOf("SQ")

    private lateinit var presenter: StockPresenter
    private var recyclerView: RecyclerView? = null

    private lateinit var adapter: QuotesAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        retainInstance = true

        val layout: View = inflater!!.inflate(R.layout.fragment_main_recycler, container, false)
        recyclerView = layout.findViewById(R.id.fragment_main_recycler_view)

        attachPresenter()

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.setHasFixedSize(true)

        val callback: ItemTouchHelper.Callback = MainItemTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return layout
    }

    override fun onStop() {
        detachPresenter()
        super.onStop()
    }

    override fun attachPresenter() {
        presenter = StockPresenterImpl(
                StockRepositoryImpl(StockServiceImpl.get()), AndroidSchedulers.mainThread(),
                Schedulers.from(Executors.newSingleThreadExecutor()), *defaultSymbols
        )
        presenter.attachView(this)

        adapter = QuotesAdapter(activity, presenter)
        recyclerView!!.adapter = adapter
    }

    override fun detachPresenter() {
        presenter.detachView()
    }

    override fun displayStockData(listOfQuotes: List<SingleQuote>) {
        adapter.swapList(listOfQuotes)
    }

    override fun logMessage(message: String) {
        Log.v("Henry ~", message)
    }
}