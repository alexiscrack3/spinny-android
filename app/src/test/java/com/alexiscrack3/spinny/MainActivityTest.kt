package com.alexiscrack3.spinny

import android.view.MenuItem
import androidx.test.core.app.ActivityScenario
import com.alexiscrack3.spinny.settings.SettingsActivity
import com.alexiscrack3.spinny.splash.SplashActivity
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.robolectric.Shadows

class MainActivityTest : SpinnyTest() {

    @Test
    fun `navigate to settings screen when selecting menu item for settings`() {
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

    @Test
    fun `onOptionsItemSelected should return true when selecting menu item for settings`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val menuItem = mock<MenuItem> {
                on { this.itemId } doReturn R.id.settings_activity
            }
            val actual = activity.onOptionsItemSelected(menuItem)

            assertThat(actual).isTrue()
        }
        activityScenario.close()
    }

    @Test
    fun `onOptionsItemSelected should return false when selecting menu item for nothing`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val menuItem = mock<MenuItem> {
                on { this.itemId } doReturn 1
            }
            val actual = activity.onOptionsItemSelected(menuItem)

            assertThat(actual).isFalse()
        }
        activityScenario.close()
    }

    @Test
    fun `onSupportNavigateUp should return false when parent activity is null`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val actual = activity.onSupportNavigateUp()

            assertThat(actual).isFalse()
        }
        activityScenario.close()
    }
}
