package com.alexiscrack3.spinny.login

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.api.Result
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
    fun `authentication should not be attempted when email is not valid`() {
        val email = ""
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(loginRepository, never()).signIn(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is not valid`() {
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
        assertThat(actual).isInstanceOf(ValidatorResult.Valid::class.java)
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
        assertThat(actual).isInstanceOf(ValidatorResult.Invalid::class.java)
    }

    @Test
    fun `valid object should be emitted when password is valid`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.passwordErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Valid::class.java)
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
        assertThat(actual).isInstanceOf(ValidatorResult.Invalid::class.java)
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
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue() as Result.Success
        assertThat(actual.data).isEqualTo(accessToken)
    }

    @Test
    fun `failure resource is emitted when authenticating user`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signIn(email, password) } doReturn Single.error(throwable)
        }
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue() as Result.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading resource is emitted when authenticating user`() {
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        val actual = testObject.authenticationState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(Result.Loading::class.java)
    }

    @Test
    fun `access token should be stored when authentication is successful`() {
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
        val testObject = LoginViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignInClicked()

        verify(securePreferences).setAccessToken(accessToken)
    }

    @Test
    fun `navigate to enrollment screen when sign up is clicked`() {
        val testObject = LoginViewModel(loginRepository, securePreferences)
        val navController = mock<NavController>()
        val view = mockk<View>()
        mockkStatic(Navigation::class)
        every { Navigation.findNavController(view) } returns navController

        testObject.onSignUpClicked(view)

        verify(navController).navigate(R.id.action_loginFragment_to_enrollmentFragment)
    }
}
