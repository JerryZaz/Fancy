package us.hnry.fancy.fragments

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.hnry.fancy.R
import us.hnry.fancy.SearchActivity
import us.hnry.fancy.adapters.QuotesAdapter
import us.hnry.fancy.data.StockRepositoryImpl
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.network.model.Symbol
import us.hnry.fancy.presentation.StockPresenter
import us.hnry.fancy.presentation.StockPresenterImpl
import us.hnry.fancy.presentation.view.StockView
import us.hnry.fancy.ui.MainItemTouchCallback
import us.hnry.fancy.utils.Utility
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashSet

/**
 * @author Henry
 * 10/8/2017
 */
class MainFragment : Fragment(), StockView, PersistentSymbolsChangedListener {
    private val mPreferences by lazy { activity.getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE) }

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
                Schedulers.from(Executors.newSingleThreadExecutor()), *getSymbolsAsArray()
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

    override fun addSymbol(symbol: Symbol) {
        val symbolsArray = addSymbolAndGet(symbol)
        presenter.symbolSetChanged(*symbolsArray)
    }

    override fun removeSymbol(symbol: Symbol) {
        val symbolsArray = removeSymbolAndGet(symbol)
        presenter.symbolSetChanged(*symbolsArray)
    }

    override fun logMessage(message: String) {
        Log.v("Henry ~", message)

        //TODO remove
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSymbolAdded(symbol: Symbol) {
        recyclerView?.let {
            Snackbar.make(it, "${symbol.symbol} will be visible after the next update",
                    Snackbar.LENGTH_SHORT).show()
        }
        addSymbol(symbol)
    }

    override fun onSymbolRemoved(symbol: Symbol) {
        removeSymbol(symbol)

        recyclerView?.let {
            Snackbar.make(it, "Symbol removed from Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { addSymbol(symbol) }
                    .show()
        }
    }

    private fun addSymbolAndGet(symbol: Symbol): Array<String> {
        val symbolsSet = HashSet<String>(getPersistentSymbolsSet())
        if (symbolsSet.contains(symbol.symbol)) {
            return symbolsSet.toTypedArray()
        }

        symbolsSet.add(symbol.symbol)
        mPreferences.edit().putStringSet(Utility.PERSISTENT_SYMBOLS_SET, symbolsSet).apply()
        return getSymbolsAsArray()
    }

    private fun removeSymbolAndGet(symbol: Symbol): Array<String> {
        val symbolsSet = HashSet<String>(getPersistentSymbolsSet())
        if (!symbolsSet.contains(symbol.symbol)) {
            return symbolsSet.toTypedArray()
        }

        symbolsSet.remove(symbol.symbol)
        mPreferences.edit().putStringSet(Utility.PERSISTENT_SYMBOLS_SET, symbolsSet).apply()
        return getSymbolsAsArray()
    }

    private fun getPersistentSymbolsSet(): MutableSet<String> {
        return mPreferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                HashSet(Arrays.asList(*Utility.DEFAULT_SYMBOLS)))
    }

    private fun getSymbolsAsArray(): Array<String> {
        return getPersistentSymbolsSet().toTypedArray()
    }
}