package us.hnry.fancy.dependency

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.hnry.fancy.data.StockDetailRepositoryImpl
import us.hnry.fancy.data.TrackedSymbolsDataSourceImpl
import us.hnry.fancy.domain.StockDetailRepository
import us.hnry.fancy.domain.TrackedSymbolsDataSource
import us.hnry.fancy.local.DatabaseManager
import us.hnry.fancy.local.dao.SearchHistoryDao
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.StockServiceImpl.StockService
import javax.inject.Named

/**
 * @author Henry
 * 10/29/2017
 */
@Module
class StockDetailModule(val context: Context) {
    @Provides
    fun stockService(): StockService = StockServiceImpl.get()

    @Provides
    fun databaseManager(): DatabaseManager = DatabaseManager(context)

    @Provides
    fun searchHistoryDao(databaseManager: DatabaseManager): SearchHistoryDao = databaseManager.searchHistoryDatabase.searchHistoryDao()

    @Provides
    @Named("observeOn")
    fun observeOnScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named("subscribeOn")
    fun subscribeOnScheduler(): Scheduler = Schedulers.io()

    @Provides
    fun sharedPreferences(): SharedPreferences = context.getSharedPreferences("savedData", Context.MODE_PRIVATE)

    @Provides
    fun symbolsDataSource(sharedPreferences: SharedPreferences): TrackedSymbolsDataSource = TrackedSymbolsDataSourceImpl(sharedPreferences)

    @Provides
    fun stockDetailRepository(stockService: StockService, dao: SearchHistoryDao, symbolsDataSource: TrackedSymbolsDataSource): StockDetailRepository = StockDetailRepositoryImpl(stockService, dao, symbolsDataSource)
}