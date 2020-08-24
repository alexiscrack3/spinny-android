package com.alexiscrack3.spinny

import android.view.MenuItem
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import com.alexiscrack3.spinny.settings.SettingsActivity
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest : SpinnyTest() {

    @Before
    override fun setUp() {
        super.setUp()
        Intents.init()
    }

    @After
    override fun tearDown() {
        super.tearDown()
        Intents.release()
    }

    @Test
    fun `navigate to settings screen when clicking on the settings menu item`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            val menuItem = mock<MenuItem> {
                on { this.itemId } doReturn R.id.settings_activity
            }
            activity.onOptionsItemSelected(menuItem)

            intended(IntentMatchers.hasComponent(SettingsActivity::class.java.name))
        }
        activityScenario.close()
    }
}
