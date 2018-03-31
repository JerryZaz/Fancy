package us.hnry.fancy

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crash.FirebaseCrash
import us.hnry.fancy.dependency.*

/**
 * @author Henry
 * 10/11/2017
 */
class Core : Application() {

    companion object {
        lateinit var searchHistoryComponent: SearchHistoryComponent
        lateinit var stockDetailComponent: StockDetailComponent
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Thread.UncaughtExceptionHandler { _, throwable -> FirebaseCrash.report(throwable) }

        searchHistoryComponent = DaggerSearchHistoryComponent.builder().searchHistoryModule(SearchHistoryModule(this)).build()
        stockDetailComponent = DaggerStockDetailComponent.builder().stockDetailModule(StockDetailModule(this)).build()
    }
}