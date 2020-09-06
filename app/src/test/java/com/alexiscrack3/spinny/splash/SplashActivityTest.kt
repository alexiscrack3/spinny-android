package com.alexiscrack3.spinny.splash

import androidx.test.core.app.ActivityScenario
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.login.LoginActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.robolectric.Shadows

class SplashActivityTest: SpinnyTest() {

    @Test
    fun `start login screen when splash screen is created`() {
        val activityScenario = ActivityScenario.launch(SplashActivity::class.java)
        activityScenario.onActivity { activity ->
            val shadowActivity = Shadows.shadowOf(activity)

            val actual = shadowActivity.nextStartedActivity

            assertThat(actual.component?.className).isEqualTo(LoginActivity::class.java.name)
        }
        activityScenario.close()
    }

    @Test
    fun `splash screen is finished when splash screen started`() {
        val activityScenario = ActivityScenario.launch(SplashActivity::class.java)
        activityScenario.onActivity { activity ->
            val actual = activity.isFinishing

            assertThat(actual).isTrue()
        }
        activityScenario.close()
    }
}