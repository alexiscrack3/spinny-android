package com.alexiscrack3.spinny.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.api.SignInResponse
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    private val email = "email@spinny.io"
    private val password = "password"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `onSignInClicked should invoke signIn on LoginRepository when email and password are valid`() {
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository).signIn(email, password)
    }

    @Test
    fun `onSignInClicked should not invoke signIn on LoginRepository when email is not valid`() {
        val email = ""
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `onSignInClicked should not invoke signIn on LoginRepository when password is not valid`() {
        val email = ""
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `successful resource with token is emitted when authenticating user`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            rating = 0
        )
        val accessToken = "accessToken"
        val signInResponse = SignInResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = Response(signInResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(response)
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data, equalTo(accessToken))
    }

    @Test
    fun `failing resource is emitted when authenticating user`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.error(throwable)
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error, equalTo(throwable))
    }

    @Test
    fun `loading resource is emitted when authenticating user`() {
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.never()
        }
        val testObject = LoginViewModel(loginRepository, mock()).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.tokenLiveData.getOrAwaitValue()
        assertThat(actual, instanceOf(Resource.Loading::class.java))
    }

    @Test
    fun `access token should be stored on successful authentication`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            rating = 0
        )
        val accessToken = "accessToken"
        val signInResponse = SignInResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = Response(signInResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(response)
        }
        val securePreferences = mock<SecurePreferences>()
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignInClicked()

        verify(securePreferences).setAccessToken(accessToken)
    }
}
