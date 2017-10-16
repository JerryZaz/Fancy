package us.hnry.fancy

import android.app.Application
import com.google.firebase.crash.FirebaseCrash

/**
 * @author Henry
 * 10/11/2017
 */
class Core : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.UncaughtExceptionHandler { _, throwable -> FirebaseCrash.report(throwable) }
    }
}