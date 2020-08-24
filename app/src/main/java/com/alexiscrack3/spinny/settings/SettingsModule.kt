package com.alexiscrack3.spinny.settings

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsModule = module {
    factory { ThemesRepository(androidContext()) }
}
