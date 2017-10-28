package us.hnry.fancy.presentation.wrapper

/**
 * @author Henry
 * 10/27/2017
 */
interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}