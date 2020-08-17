package com.alexiscrack3.spinny.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.alexiscrack3.spinny.R

class SettingsFragment : PreferenceFragmentCompat() {
    private val sharedPreferenceChangeListener: (SharedPreferences, String) -> Unit = { sharedPreferences, key ->
        when (key) {
            "themes_list" -> {
                val mode = sharedPreferences.getString("themes_list", "-1")?.toInt() ?: -1
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(requireContext(), R.xml.preferences, false)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onStart() {
        super.onStart()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }
}
