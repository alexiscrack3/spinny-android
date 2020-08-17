package com.alexiscrack3.spinny

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SpinnyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@SpinnyApplication)
        }
        SpinnyModule.init()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val mode = prefs.getString("themes_list", "-1")?.toInt() ?: -1
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
