package com.alexiscrack3.spinny.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.security.InvalidParameterException

class ThemesRepositoryTest : SpinnyTest() {

    @Test
    fun `mode from preferences should be MODE_NIGHT_FOLLOW_SYSTEM when value is null`() {
        val testObject = ThemesRepository(context)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
            .putString(context.getString(R.string.themes_preference_key), null)
            .commit()

        val actual = testObject.getModeFromPreferences()

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun `mode from preferences should be MODE_NIGHT_YES when value is dark`() {
        val testObject = ThemesRepository(context)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
            .putString(context.getString(R.string.themes_preference_key), context.getString(R.string.dark_entry_value))
            .commit()

        val actual = testObject.getModeFromPreferences()

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_YES)
    }

    @Test
    fun `mode from preferences should be MODE_NIGHT_NO when value is light`() {
        val testObject = ThemesRepository(context)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
            .putString(context.getString(R.string.themes_preference_key), context.getString(R.string.light_entry_value))
            .commit()

        val actual = testObject.getModeFromPreferences()

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Test
    fun `mode from preferences should be MODE_NIGHT_FOLLOW_SYSTEM when value is system`() {
        val testObject = ThemesRepository(context)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
            .putString(context.getString(R.string.themes_preference_key), context.getString(R.string.system_entry_value))
            .commit()

        val actual = testObject.getModeFromPreferences()

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun `mode should be MODE_NIGHT_YES when value is dark`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getModeForValue(context.getString(R.string.dark_entry_value))

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_YES)
    }

    @Test
    fun `mode should be MODE_NIGHT_NO when value is light`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getModeForValue(context.getString(R.string.light_entry_value))

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Test
    fun `mode should be MODE_NIGHT_FOLLOW_SYSTEM when value is system`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getModeForValue(context.getString(R.string.system_entry_value))

        assertThat(actual).isEqualTo(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun `invalid parameter exception should be thrown when value is unknown`() {
        val value = "value"
        val testObject = ThemesRepository(context)

        try {
            testObject.getModeForValue(value)
        } catch (ex: InvalidParameterException) {
            assertThat(ex).isInstanceOf(InvalidParameterException::class.java)
            assertThat(ex.message).isEqualTo("Theme not defined for $value")
        }
    }

    @Test
    fun `description should be MODE_NIGHT_YES when value is dark`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getDescriptionForValue(context.getString(R.string.dark_entry_value))

        assertThat(actual).isEqualTo("Dark")
    }

    @Test
    fun `description should be MODE_NIGHT_NO when value is light`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getDescriptionForValue(context.getString(R.string.light_entry_value))

        assertThat(actual).isEqualTo("Light")
    }

    @Test
    fun `description should be MODE_NIGHT_FOLLOW_SYSTEM when value is system`() {
        val testObject = ThemesRepository(context)

        val actual = testObject.getDescriptionForValue(context.getString(R.string.system_entry_value))

        assertThat(actual).isEqualTo("System default")
    }
}
