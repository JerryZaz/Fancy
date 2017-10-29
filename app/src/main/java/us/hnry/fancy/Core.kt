package us.hnry.fancy

import android.app.Application
import com.google.firebase.crash.FirebaseCrash
import us.hnry.fancy.dependency.DaggerSearchHistoryComponent
import us.hnry.fancy.dependency.SearchHistoryComponent
import us.hnry.fancy.dependency.SearchHistoryModule

/**
 * @author Henry
 * 10/11/2017
 */
class Core : Application() {

    companion object {
        lateinit var searchHistoryComponent: SearchHistoryComponent
    }

    override fun onCreate() {
        super.onCreate()
        Thread.UncaughtExceptionHandler { _, throwable -> FirebaseCrash.report(throwable) }

        searchHistoryComponent = DaggerSearchHistoryComponent.builder().searchHistoryModule(SearchHistoryModule(this)).build()
    }
}