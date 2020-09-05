package com.alexiscrack3.spinny

import android.view.MenuItem
import androidx.test.core.app.ActivityScenario
import com.alexiscrack3.spinny.settings.SettingsActivity
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.robolectric.Shadows

class MainActivityTest : SpinnyTest() {

    @Test
    fun `navigate to settings screen when clicking on the settings menu item`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val shadowActivity = Shadows.shadowOf(activity)
            val menuItem = mock<MenuItem> {
                on { this.itemId } doReturn R.id.settings_activity
            }
            activity.onOptionsItemSelected(menuItem)

            val actual = shadowActivity.nextStartedActivity

            assertThat(actual.component?.className).isEqualTo(SettingsActivity::class.java.name)
        }
        activityScenario.close()
    }
}
