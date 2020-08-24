package com.alexiscrack3.spinny.settings

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.utils.isUpButtonEnabled
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
class SettingsActivityTest : SpinnyTest() {

    @Test
    fun `activity's title should be settings`() {
        val activityScenario = ActivityScenario.launch(SettingsActivity::class.java)
        activityScenario.onActivity { activity ->
            assertThat(activity.title).isEqualTo("Settings")
        }
        activityScenario.close()
    }

    @Test
    fun `up button is enabled when action bar is set`() {
        val activityScenario = ActivityScenario.launch(SettingsActivity::class.java)
        activityScenario.onActivity { activity ->
            val actual = activity.supportActionBar?.isUpButtonEnabled()
            assertThat(actual).isTrue()
        }
        activityScenario.close()
    }

    @Test
    fun `settings screen is finished when clicking on up button`() {
        val activityScenario = ActivityScenario.launch(SettingsActivity::class.java)
        activityScenario.onActivity { activity ->
            activity.onSupportNavigateUp()
            assertThat(activity.isFinishing).isTrue()
        }
        activityScenario.close()
    }
}
