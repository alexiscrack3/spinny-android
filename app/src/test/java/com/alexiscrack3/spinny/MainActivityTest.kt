package com.alexiscrack3.spinny

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun `title is set`() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.onActivity { activity ->
                assertThat(activity.title.toString()).isEqualTo("Main")
            }
        }
    }
}
