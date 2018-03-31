package us.hnry.fancy.presentation

import com.google.firebase.crash.FirebaseCrash
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import us.hnry.fancy.data.SearchHistoryRepositoryImpl
import us.hnry.fancy.domain.interactor.SearchHistoryUseCase
import us.hnry.fancy.presentation.view.SearchHistoryView
import javax.inject.Inject
import javax.inject.Named

/**
 * @author Henry
 * 10/27/2017
 */
class SearchHistoryPresenterImpl @Inject constructor(repository: SearchHistoryRepositoryImpl, @Named("observeOn") observeOn: Scheduler, @Named("subscribeOn") subscribeOn: Scheduler) : SearchHistoryPresenter, BasePresenterImpl<SearchHistoryView>() {
    private val useCase: SearchHistoryUseCase by lazy { SearchHistoryUseCase(repository, subscribeOn, observeOn) }

    override fun attachView(view: SearchHistoryView) {
        super.attachView(view)

        disposables.add(useCase.execute(null).subscribeBy(onNext = {
            mView?.loadHistoryItems(it)
        }, onError = {
            mView?.showErrorMessage(it.message)
            FirebaseCrash.report(it)
        }))
    }

    override fun historyRowItemClicked(symbol: String) {
        mView?.launchStockDetail(symbol)
    }
}