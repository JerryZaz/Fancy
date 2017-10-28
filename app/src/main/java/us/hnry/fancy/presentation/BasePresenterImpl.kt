package us.hnry.fancy.presentation

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Henry
 * 10/27/2017
 */
abstract class BasePresenterImpl<V> {
    internal val disposables by lazy { CompositeDisposable() }
    internal var mView: V? = null

    @CallSuper
    open fun attachView(view: V) {
        mView = view
    }

    @CallSuper
    fun detachView() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}