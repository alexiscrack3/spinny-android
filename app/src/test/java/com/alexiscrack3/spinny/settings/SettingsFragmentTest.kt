package com.alexiscrack3.spinny.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.preference.ListPreference
import com.alexiscrack3.spinny.SpinnyTest
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.test.mock.declareMock

class SettingsFragmentTest : SpinnyTest() {
    private val themesRepository by inject<ThemesRepository>()
    private val themesPreferenceKey = "themes_preference"

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<ThemesRepository>()
    }

    @Test
    fun `default value of themes preference should be -1`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val defaultValue = themesPreference.value
            assertThat(defaultValue).isEqualTo("-1")
        }
    }

    @Test
    fun `title of themes preference should be set`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val title = themesPreference.title
            assertThat(title).isEqualTo("Choose theme")
        }
    }

    @Test
    fun `entries of themes preference should be dark, light and system default`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val entries = themesPreference.entries
            assertThat(entries).asList().containsExactly("Dark", "Light", "System default")
        }
    }

    @Test
    fun `entry values of themes preference should be 2, 1 and -1`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val entryValues = themesPreference.entryValues
            assertThat(entryValues).asList().containsExactly("2", "1", "-1")
        }
    }

    @Test
    fun `night mode should be set to new value`() {
        val newValue = "2"
        whenever(themesRepository.getModeForValue(newValue)).thenReturn(AppCompatDelegate.MODE_NIGHT_YES)
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val onPreferenceChangeListener = themesPreference.onPreferenceChangeListener
            onPreferenceChangeListener.onPreferenceChange(themesPreference, newValue)

            val actual = AppCompatDelegate.getDefaultNightMode()

            assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    @Test
    fun `night mode should be changed when preference changes`() {
        val newValue = "2"
        whenever(themesRepository.getModeForValue(newValue)).thenReturn(AppCompatDelegate.MODE_NIGHT_YES)
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            val onPreferenceChangeListener = themesPreference.onPreferenceChangeListener

            val actual = onPreferenceChangeListener.onPreferenceChange(themesPreference, newValue)

            assertThat(actual).isTrue()
        }
    }

    @Test
    fun `summary provider should be set to theme's description`() {
        val expected = "description"
        val value = "2"
        whenever(themesRepository.getDescriptionForValue(value)).thenReturn(expected)
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            themesPreference.value = value

            val actual = themesPreference.summaryProvider?.provideSummary(themesPreference)

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `summary provider should not be set to theme's description when `() {
        val expected = "description"
        val value = "2"
        whenever(themesRepository.getDescriptionForValue(value)).thenReturn(expected)
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference(themesPreferenceKey)!!
            themesPreference.value = value

            val actual = themesPreference.summaryProvider?.provideSummary(themesPreference)

            assertThat(actual).isEqualTo(expected)
        }
    }
}
