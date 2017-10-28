package us.hnry.fancy.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import us.hnry.fancy.Core
import us.hnry.fancy.R
import us.hnry.fancy.adapters.SearchHistoryAdapter
import us.hnry.fancy.domain.model.HistoryEntry
import us.hnry.fancy.presentation.SearchHistoryPresenterImpl
import us.hnry.fancy.presentation.view.SearchHistoryView
import us.hnry.fancy.presentation.wrapper.SearchHistoryRowItem
import us.hnry.fancy.ui.DividerItemDecoration
import javax.inject.Inject

/**
 * @author Henry
 * 10/27/2017
 */
class SearchHistoryFragment : Fragment(), SearchHistoryView {
    @Inject
    lateinit var presenter: SearchHistoryPresenterImpl

    lateinit var recyclerView: RecyclerView

    val adapter: SearchHistoryAdapter by lazy { SearchHistoryAdapter(activity) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Core.searchHistoryComponent.inject(this)

        val layout: View = inflater!!.inflate(R.layout.fragment_search_history, container, false)
        recyclerView = layout.findViewById(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity, null))
        recyclerView.adapter = adapter

        presenter.attachView(this)

        return layout
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun loadHistoryItems(items: List<HistoryEntry>) {
        val asList = mutableListOf<SearchHistoryRowItem>()
        items.forEach { asList.add(SearchHistoryRowItem(it)) }
        adapter.swapList(asList)
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}