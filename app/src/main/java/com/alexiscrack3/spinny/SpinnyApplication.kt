package com.alexiscrack3.spinny

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.alexiscrack3.spinny.settings.ThemesRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SpinnyApplication : MultiDexApplication() {
    private val themesRepository by inject<ThemesRepository>()

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

        val mode = themesRepository.getNightModeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
