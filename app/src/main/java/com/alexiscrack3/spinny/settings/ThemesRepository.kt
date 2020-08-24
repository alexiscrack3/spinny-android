package com.alexiscrack3.spinny.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.alexiscrack3.spinny.R
import java.security.InvalidParameterException

class ThemesRepository(
    private val context: Context
) {

    fun getModeFromPreferences(): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val value = sharedPreferences.getString(
            context.getString(R.string.themes_preference_key),
            null
        )
        return value?.let {
            getModeForValue(it)
        } ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun getModeForValue(value: String): Int = when (value) {
        context.getString(R.string.dark_entry_value) -> AppCompatDelegate.MODE_NIGHT_YES
        context.getString(R.string.light_entry_value) -> AppCompatDelegate.MODE_NIGHT_NO
        context.getString(R.string.system_entry_value) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> throw InvalidParameterException("Theme not defined for $value")
    }

    fun getDescriptionForValue(value: String?): String = when (value) {
        context.getString(R.string.dark_entry_value) -> context.getString(R.string.dark_entry)
        context.getString(R.string.light_entry_value) -> context.getString(R.string.light_entry)
        else -> context.getString(R.string.system_entry)
    }
}
