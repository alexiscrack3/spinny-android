package com.alexiscrack3.spinny.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.alexiscrack3.spinny.R
import org.koin.android.ext.android.inject

class SettingsFragment : PreferenceFragmentCompat() {
    private val themesRepository by inject<ThemesRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(requireContext(), R.xml.preferences, false)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onStart() {
        super.onStart()
        val themesPreferenceKey = requireContext().getString(R.string.themes_preference_key)
        val themesPreference = findPreference<ListPreference>(themesPreferenceKey)
        themesPreference?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is String) {
                val mode = themesRepository.getModeForValue(newValue)
                AppCompatDelegate.setDefaultNightMode(mode)
            }
            true
        }
        themesPreference?.summaryProvider = getThemesPreferenceSummaryProvider()
    }

    private fun getThemesPreferenceSummaryProvider(): Preference.SummaryProvider<ListPreference> {
        return Preference.SummaryProvider { preference ->
            themesRepository.getDescriptionForValue(preference.value)
        }
    }
}
