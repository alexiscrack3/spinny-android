package com.alexiscrack3.spinny.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.api.SignInResponse
import com.alexiscrack3.spinny.api.UserResponse
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    private val email = ""
    private val password = ""

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `onSignInClicked should invoke signIn on LoginRepository`() {
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository)

        testObject.onSignInClicked()

        verify(loginRepository).signIn(email, password)
    }

    @Test
    fun `successful resource with token is emitted when authenticating user`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            rating = 0
        )
        val token = "token"
        val userResponse = UserResponse(
            user = playerResponse,
            token = token
        )
        val signInResponse = SignInResponse(userResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(signInResponse)
        }
        val testObject = LoginViewModel(loginRepository)

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data, equalTo(token))
    }

    @Test
    fun `failing resource is emitted when authenticating user`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.error(throwable)
        }
        val testObject = LoginViewModel(loginRepository)

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error, equalTo(throwable))
    }

    @Test
    fun `loading resource is emitted when authenticating user`() {
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository)

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue()
        assertThat(actual, instanceOf(Resource.Loading::class.java))
    }
}
