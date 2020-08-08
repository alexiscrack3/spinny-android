package com.alexiscrack3.spinny.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.api.SignUpResponse
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EnrollmentViewModelTest {
    private val loginRepository = mock<LoginRepository>()
    private val securePreferences = mock<SecurePreferences>()
    private val email = "email@spinny.io"
    private val password = "password"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(loginRepository.signUp(email, password)).thenReturn(Single.never())
    }

    @Test
    fun `authentication should be attempted when email and password are valid`() {
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when email is not valid`() {
        val email = ""
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is not valid`() {
        val password = ""
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `valid object should be emitted when email is valid`() {
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.emailErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Valid::class.java)
    }

    @Test
    fun `invalid object should be emitted when email is not valid`() {
        val email = ""
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.emailErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Invalid::class.java)
    }

    @Test
    fun `valid object should be emitted when password is valid`() {
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.passwordErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Valid::class.java)
    }

    @Test
    fun `invalid object should be emitted when password is not valid`() {
        val password = ""
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.passwordErrorState.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Invalid::class.java)
    }

    @Test
    fun `successful resource with token is emitted after authenticating user`() {
        val playerResponse = PlayerResponse(
            id = "",
            email = "",
            rating = 0
        )
        val accessToken = "accessToken"
        val signUpResponse = SignUpResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = Response(signUpResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.just(response)
        }
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.enrollmentState.getOrAwaitValue() as Result.Success
        assertThat(actual.data).isEqualTo(accessToken)
    }

    @Test
    fun `failure resource should be emitted when authentication fails`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.error(throwable)
        }
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.enrollmentState.getOrAwaitValue() as Result.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading resource should be emitted before authenticating user`() {
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.enrollmentState.getOrAwaitValue()
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
        val signUpResponse = SignUpResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = Response(signUpResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.just(response)
        }
        val testObject = EnrollmentViewModel(loginRepository, securePreferences).apply {
            emailState.value = email
            passwordState.value = password
        }

        testObject.onSignUpClicked()

        verify(securePreferences).setAccessToken(accessToken)
    }
}
