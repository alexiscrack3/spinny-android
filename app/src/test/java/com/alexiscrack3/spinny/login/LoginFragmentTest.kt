package com.alexiscrack3.spinny.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Result
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock

class LoginFragmentTest : SpinnyTest() {
    private val loginViewModel by inject<LoginViewModel>()
    private val tokenLiveData = MutableLiveData<Result<String>>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<LoginViewModel>()
        whenever(loginViewModel.tokenLiveData).doReturn(tokenLiveData)
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
}
