package us.hnry.fancy.dependency

import dagger.Component
import us.hnry.fancy.fragments.SearchHistoryFragment
import us.hnry.fancy.local.dao.SearchHistoryDao
import javax.inject.Singleton

/**
 * @author Henry
 * 10/27/2017
 */
@Singleton
@Component(modules = arrayOf(SearchHistoryModule::class))
interface SearchHistoryComponent {
    fun inject(fragment: SearchHistoryFragment)
    fun dao(): SearchHistoryDao
}