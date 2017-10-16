package us.hnry.fancy.fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.hnry.fancy.R
import us.hnry.fancy.activity.SearchActivity
import us.hnry.fancy.adapters.QuotesAdapter
import us.hnry.fancy.data.StockRepositoryImpl
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.network.model.Symbol
import us.hnry.fancy.presentation.StockPresenter
import us.hnry.fancy.presentation.StockPresenterImpl
import us.hnry.fancy.presentation.view.StockView
import us.hnry.fancy.ui.MainItemTouchCallback
import us.hnry.fancy.utils.SymbolsHelper
import us.hnry.fancy.utils.Utility
import java.util.concurrent.Executors

/**
 * @author Henry
 * 10/8/2017
 */
class MainFragment : Fragment(), StockView, PersistentSymbolsChangedListener {
    private val symbolsHelper by lazy { SymbolsHelper(activity, this) }

    private lateinit var presenter: StockPresenter
    private var recyclerView: RecyclerView? = null

    private lateinit var adapter: QuotesAdapter
    private var searchFab: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        retainInstance = true

        val layout: View = inflater!!.inflate(R.layout.fragment_main_recycler, container, false)
        recyclerView = layout.findViewById(R.id.fragment_main_recycler_view)

        searchFab = activity.findViewById(R.id.search_fab)
        searchFab?.setOnClickListener({
            startActivity(Intent(activity, SearchActivity::class.java).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH))
        })

        initPresenter()
        adapter = QuotesAdapter(activity, this)
        recyclerView!!.adapter = adapter

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.setHasFixedSize(true)

        val callback: ItemTouchHelper.Callback = MainItemTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return layout
    }

    private fun initPresenter() {
        presenter = StockPresenterImpl(
                StockRepositoryImpl(StockServiceImpl.get()), AndroidSchedulers.mainThread(),
                Schedulers.from(Executors.newSingleThreadExecutor()), *symbolsHelper.getPersistentSymbolsSetAsArray()
        )
    }

    override fun onResume() {
        attachPresenter()
        super.onResume()
    }

    override fun onStop() {
        detachPresenter()
        super.onStop()
    }

    override fun attachPresenter() {
        presenter.attachView(this)
    }

    override fun detachPresenter() {
        presenter.detachView()
    }

    override fun displayStockData(listOfQuotes: List<SingleQuote>) {
        adapter.swapList(listOfQuotes)
    }

    override fun logMessage(message: String) {
        //TODO remove
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSymbolAdded(symbol: Symbol) {
        presenter.symbolSetChanged(*symbolsHelper.getPersistentSymbolsSetAsArray())

        recyclerView?.let {
            Snackbar.make(it, "${symbol.symbol} will be visible after the next update",
                    Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onSymbolRemoved(symbol: Symbol) {
        presenter.symbolSetChanged(*symbolsHelper.getPersistentSymbolsSetAsArray())

        recyclerView?.let {
            Snackbar.make(it, "Symbol removed from Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { symbolsHelper.addSymbol(symbol) }
                    .show()
        }
    }
}