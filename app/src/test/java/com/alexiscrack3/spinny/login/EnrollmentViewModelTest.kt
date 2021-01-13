package com.alexiscrack3.spinny.login

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.api.SignUpResponse
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.alexiscrack3.spinny.utils.test
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EnrollmentViewModelTest : SpinnyTest() {
    private val loginRepository = mock<LoginRepository>()
    private val securePreferences = mock<SecurePreferences>()
    private val testScheduler = TestScheduler()
    private val email = "email@spinny.io"
    private val password = "password"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        whenever(loginRepository.signUp(email, password)).thenReturn(Single.never())
    }

    @Test
    fun `authentication should be attempted when email and password are valid`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when email is null`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = null
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when email is empty`() {
        val email = ""
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is null`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = null
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `authentication should not be attempted when password is empty`() {
        val password = ""
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        verify(loginRepository, never()).signUp(email, password)
    }

    @Test
    fun `valid object should be emitted when email is valid`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.emailErrorLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `invalid object should be emitted when email is not valid`() {
        val email = ""
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.emailErrorLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Failure::class.java)
    }

    @Test
    fun `valid object should be emitted when password is valid`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.passwordErrorLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `invalid object should be emitted when password is not valid`() {
        val password = ""
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.passwordErrorLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(ValidatorResult.Failure::class.java)
    }

    @Test
    fun `successful resource with token is emitted after authenticating user`() {
        val playerResponse = PlayerResponse.test()
        val accessToken = "accessToken"
        val signUpResponse = SignUpResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = ApiResponse(signUpResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.just(response)
        }
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()
        testScheduler.triggerActions()

        val actual = testObject.enrollmentLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data).isEqualTo(accessToken)
    }

    @Test
    fun `failure resource should be emitted when authentication fails`() {
        val throwable = Throwable()
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.error(throwable)
        }
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()
        testScheduler.triggerActions()

        val actual = testObject.enrollmentLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading resource should be emitted before authenticating user`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()

        val actual = testObject.enrollmentLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
    }

    @Test
    fun `access token should be stored when authentication is successful`() {
        val playerResponse = PlayerResponse.test()
        val accessToken = "accessToken"
        val signUpResponse = SignUpResponse(
            user = playerResponse,
            token = accessToken
        )
        val response = ApiResponse(signUpResponse)
        val loginRepository = mock<LoginRepository> {
            on { this.signUp(email, password) } doReturn Single.just(response)
        }
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        testObject.onSignUpClicked()
        testScheduler.triggerActions()

        verify(securePreferences).setAccessToken(accessToken)
    }

    @Test
    fun `navigate to login screen when sign in is clicked`() {
        val testObject = EnrollmentViewModel(
            loginRepository = loginRepository,
            securePreferences = securePreferences,
            scheduler = testScheduler
        ).apply {
            emailLiveData.value = email
            passwordLiveData.value = password
        }

        val navController = mock<NavController>()
        val view = mockk<View>()
        mockkStatic(Navigation::class)
        every { Navigation.findNavController(view) } returns navController

        testObject.onSignInClicked(view)

        verify(navController).popBackStack()
    }
}
