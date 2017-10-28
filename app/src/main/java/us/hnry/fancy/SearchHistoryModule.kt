package us.hnry.fancy

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import us.hnry.fancy.data.SearchHistoryRepositoryImpl
import us.hnry.fancy.domain.SearchHistoryRepository
import us.hnry.fancy.local.DatabaseManager
import us.hnry.fancy.local.dao.SearchHistoryDao
import javax.inject.Named

/**
 * @author Henry
 * 10/27/2017
 */
@Module
class SearchHistoryModule(val context: Context) {
    @Provides
    fun provideDatabaseManager(): DatabaseManager = DatabaseManager(context)

    @Provides
    fun provideSearchHistoryDao(databaseManager: DatabaseManager): SearchHistoryDao = databaseManager.searchHistoryDatabase.searchHistoryDao()

    @Provides
    fun provideSearchHistoryRepository(dao: SearchHistoryDao): SearchHistoryRepository = SearchHistoryRepositoryImpl(dao)

    @Provides
    @Named("observeOn")
    fun provideObserveOnScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @Named("subscribeOn")
    fun provideSubscribeOnScheduler() = Schedulers.io()
}