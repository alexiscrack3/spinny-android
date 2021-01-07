package com.alexiscrack3.spinny.login

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.api.SignInResponse
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class LoginViewModelTest {
    private val loginRepository = mock<LoginRepository>()
    private val securePreferences = mock<SecurePreferences>()
    private val email = "email@spinny.io"
    private val password = "password"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(loginRepository.signIn(email, password)).thenReturn(Single.never())
    }

    @Test
    fun `authentication should be attempted when email and password are valid`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository).signIn(email, password)
    }

    @Test
    fun `authentication should not be attempted when email is null`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = null
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `authentication should not be attempted when email is empty`() {
        val email = ""
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is null`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = null
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is empty`() {
        val password = ""
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `valid object should be emitted when email is valid`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.emailErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `invalid object should be emitted when email is not valid`() {
        val email = ""
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.emailErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Failure::class.java)
    }

    @Test
    fun `valid object should be emitted when password is valid`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.passwordErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `invalid object should be emitted when password is not valid`() {
        val password = ""
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.passwordErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Failure::class.java)
    }

    @Test
    fun `email and password should be cleared when authentication is successful`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            firstName = null,
            lastName = null,
            rating = 0,
            createdAt = Date()
        )
        val accessToken = "accessToken"
        val signInResponse = SignInResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = ApiResponse(signInResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(response)
        }
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actualEmail = testObject.emailState.getOrAwaitValue()
        val actualPassword = testObject.emailState.getOrAwaitValue()
        assertThat(actualEmail).isEmpty()
        assertThat(actualPassword).isEmpty()
    }

    @Test
    fun `access token should be stored when authentication is successful`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            firstName = null,
            lastName = null,
            rating = 0,
            createdAt = Date()
        )
        val accessToken = "accessToken"
        val signInResponse = SignInResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = ApiResponse(signInResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(response)
        }
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(securePreferences).setAccessToken(accessToken)
    }

    @Test
    fun `successful resource with token is emitted when authentication is successful`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            firstName = null,
            lastName = null,
            rating = 0,
            createdAt = Date()
        )
        val accessToken = "accessToken"
        val signInResponse = SignInResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = ApiResponse(signInResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.just(response)
        }
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue() as Resource.Success
        assertThat(actual.data).isEqualTo(accessToken)
    }

    @Test
    fun `failure resource is emitted when authentication fails`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.error(throwable)
        }
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading resource is emitted while authenticating user`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
    }

    @Test
    fun `navigate to enrollment screen when sign up is clicked`() {
        val testObject = LoginViewModel(loginRepository, securePreferences)
        val navController = mock<NavController>()
        val view = mockk<View>()
        mockkStatic(Navigation::class)
        every { Navigation.findNavController(view) } returns navController

        testObject.onSignUpClicked(view)

        verify(navController).navigate(LoginFragmentDirections.actionLoginFragmentToEnrollmentFragment())
    }
}
