package us.hnry.fancy.dependency

import dagger.Component
import us.hnry.fancy.fragments.StockDetailFragment
import javax.inject.Singleton

/**
 * @author Henry
 * 10/29/2017
 */
@Singleton
@Component(modules = arrayOf(StockDetailModule::class))
interface StockDetailComponent {
    fun inject(fragment: StockDetailFragment)
}