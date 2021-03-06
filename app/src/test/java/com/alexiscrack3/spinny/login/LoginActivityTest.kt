package com.alexiscrack3.spinny.login

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import com.alexiscrack3.spinny.SpinnyTest
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock

class LoginActivityTest : SpinnyTest() {

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<LoginViewModel> {
            given(this.authenticationState).willReturn(MutableLiveData())
            given(this.emailErrorState).willReturn(MutableLiveData())
            given(this.passwordErrorState).willReturn(MutableLiveData())
        }
    }

    @Test
    fun `action bar should be hidden when login screen is started`() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        activityScenario.onActivity { activity ->
            assertThat(activity.supportActionBar).isNull()
        }
    }
}
