package com.alexiscrack3.spinny.settings

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.preference.ListPreference
import com.alexiscrack3.spinny.SpinnyTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SettingsFragmentTest : SpinnyTest() {

    @Test
    fun `default value of themes preference should be -1`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference("themes_preference")!!
            val defaultValue = themesPreference.value
            assertThat(defaultValue).isEqualTo("-1")
        }
    }

//    @Test
//    fun `night mode should be set to new value`() {
//        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
//        fragmentScenario.onFragment { fragment ->
//            val themesPreference: ListPreference = fragment.findPreference("themes_preference")!!
//            val index = themesPreference.entryValues.indexOf("2")
//            themesPreference.setValueIndex(index)
//
//            val actual = AppCompatDelegate.getDefaultNightMode()
//
//            assertThat(actual).isEqualTo(2)
//        }
//    }

    @Test
    fun `title of themes preference should be set`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference("themes_preference")!!
            val title = themesPreference.title
            assertThat(title).isEqualTo("Choose theme")
        }
    }

    @Test
    fun `entries of themes preference should be dark, light and system default`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference("themes_preference")!!
            val entries = themesPreference.entries
            assertThat(entries).asList().containsExactly("Dark", "Light", "System default")
        }
    }

    @Test
    fun `entry values of themes preference should be 2, 1 and -1`() {
        val fragmentScenario = launchFragmentInContainer<SettingsFragment>()
        fragmentScenario.onFragment { fragment ->
            val themesPreference: ListPreference = fragment.findPreference("themes_preference")!!
            val entryValues = themesPreference.entryValues
            assertThat(entryValues).asList().containsExactly("2", "1", "-1")
        }
    }
}
