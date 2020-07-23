package com.alexiscrack3.spinny.login

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import com.alexiscrack3.spinny.SpinnyTest
import com.nhaarman.mockitokotlin2.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock

class LoginActivityTest : SpinnyTest() {

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<LoginViewModel> {
            given(this.tokenLiveData).willReturn(MutableLiveData())
        }
    }

    @Test
    fun `action bar should be hidden when screen is started`() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        activityScenario.onActivity { activity ->
            assertThat(activity.supportActionBar?.isShowing, equalTo(false))
        }
    }

    @Test
    fun `action bar should be shown when screen is paused`() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        activityScenario.onActivity { activity ->
            activityScenario.moveToState(Lifecycle.State.DESTROYED)

            assertThat(activity.supportActionBar?.isShowing, equalTo(true))
        }
    }
}
