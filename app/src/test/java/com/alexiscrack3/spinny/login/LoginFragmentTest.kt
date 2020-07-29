package com.alexiscrack3.spinny.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock

class LoginFragmentTest : SpinnyTest() {
    private val loginViewModel by inject<LoginViewModel>()
    private val tokenLiveData = MutableLiveData<Result<String>>()
    private val emailError = MutableLiveData<ValidatorResult>()
    private val passwordError = MutableLiveData<ValidatorResult>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<LoginViewModel>()
        whenever(loginViewModel.tokenLiveData).doReturn(tokenLiveData)
        whenever(loginViewModel.emailError).doReturn(emailError)
        whenever(loginViewModel.passwordError).doReturn(passwordError)
    }

    @Test
    fun `onSignInClicked should be invoked when clicking on sign in button`() {
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment { fragment ->
            val signInButton = fragment.view!!.login_sign_in_button

            signInButton.performClick()

            verify(loginViewModel).onSignInClicked()
        }
    }

    @Test
    fun `navigate to clubs screen when clicking on sign in`() {
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.login_nav_graph)
        }
        val fragmentScenario = launchFragmentInContainer {
            LoginFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }
            }
        }
        fragmentScenario.onFragment {
            tokenLiveData.value = Result.Success("")

            assertThat(
                navController.currentDestination?.id,
                equalTo(R.id.clubsFragment)
            )
        }
    }

    @Test
    fun `email error should be shown if email is not valid`() {
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment { fragment ->
            val emailLayout = fragment.view!!.login_email_layout

            emailError.value = ValidatorResult.Invalid

            assertThat(emailLayout.error.toString(), equalTo("Email is invalid"))
        }
    }

    @Test
    fun `email error should be hidden if email is valid`() {
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment { fragment ->
            val emailLayout = fragment.view!!.login_email_layout

            emailError.value = ValidatorResult.Invalid

            assertThat(emailLayout.error?.isNotEmpty(), equalTo(true))

            emailError.value = ValidatorResult.Valid

            assertThat(emailLayout.error, nullValue())
        }
    }

    @Test
    fun `password error should be shown if email is not valid`() {
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment { fragment ->
            val passwordLayout = fragment.view!!.login_password_layout

            passwordError.value = ValidatorResult.Invalid

            assertThat(passwordLayout.error.toString(), equalTo("Password is invalid"))
        }
    }

    @Test
    fun `password error should be hidden if password is valid`() {
        val fragmentScenario = launchFragmentInContainer<LoginFragment>()
        fragmentScenario.onFragment { fragment ->
            val passwordLayout = fragment.view!!.login_password_layout

            passwordError.value = ValidatorResult.Invalid

            assertThat(passwordLayout.error?.isNotEmpty(), equalTo(true))

            passwordError.value = ValidatorResult.Valid

            assertThat(passwordLayout.error, nullValue())
        }
    }
}
