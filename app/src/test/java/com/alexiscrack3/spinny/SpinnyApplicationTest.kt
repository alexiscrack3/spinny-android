package com.alexiscrack3.spinny

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexiscrack3.spinny.settings.ThemesRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
class SpinnyApplicationTest : AutoCloseKoinTest() {
    private val themesRepository by inject<ThemesRepository>()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun setUp() {
        declareMock<ThemesRepository>()
    }

    @Test
    fun `default night mode is set when application is create`() {
        val expected = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        whenever(themesRepository.getNightModeFromPreferences()).thenReturn(expected)

        val actual = AppCompatDelegate.getDefaultNightMode()

        assertThat(actual).isEqualTo(expected)
    }
}
