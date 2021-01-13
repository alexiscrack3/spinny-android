package com.alexiscrack3.spinny.login

import android.content.DialogInterface
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.MainActivity
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.android.synthetic.main.fragment_enrollment.view.*
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowDialog

class EnrollmentFragmentTest : SpinnyTest() {
    private val enrollmentViewModel by inject<EnrollmentViewModel>()
    private val enrollmentLiveData = MutableLiveData<Resource<String>>()
    private val emailErrorLiveData = MutableLiveData<ValidatorResult>()
    private val passwordErrorLiveData = MutableLiveData<ValidatorResult>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<EnrollmentViewModel>()
        whenever(enrollmentViewModel.enrollmentLiveData).doReturn(enrollmentLiveData)
        whenever(enrollmentViewModel.emailErrorLiveData).doReturn(emailErrorLiveData)
        whenever(enrollmentViewModel.passwordErrorLiveData).doReturn(passwordErrorLiveData)
    }

    @Test
    fun `onSignUpClicked should be invoked when clicking on sign up button`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val signUpButton = fragment.view!!.enrollment_sign_up_button

            signUpButton.performClick()

            verify(enrollmentViewModel).onSignUpClicked()
        }
    }

    @Test
    fun `main screen is launched on successful authentication`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val shadowActivity = Shadows.shadowOf(fragment.activity)
            enrollmentLiveData.value = Resource.Success("")

            val actual = shadowActivity.nextStartedActivity

            assertThat(actual.component?.className).isEqualTo(MainActivity::class.java.name)
        }
    }

    @Test
    fun `email error should be shown if email is not valid`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val emailLayout = fragment.view!!.enrollment_email_layout

            emailErrorLiveData.value = ValidatorResult.Failure

            assertThat(emailLayout.error.toString()).isEqualTo("Email is invalid")
        }
    }

    @Test
    fun `email error should be hidden if email is valid`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val emailLayout = fragment.view!!.enrollment_email_layout

            emailErrorLiveData.value = ValidatorResult.Failure

            assertThat(emailLayout.error?.isNotEmpty()).isEqualTo(true)

            emailErrorLiveData.value = ValidatorResult.Success

            assertThat(emailLayout.error).isNull()
        }
    }

    @Test
    fun `password error should be shown if email is not valid`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val passwordLayout = fragment.view!!.enrollment_password_layout

            passwordErrorLiveData.value = ValidatorResult.Failure

            assertThat(passwordLayout.error.toString()).isEqualTo("Password is invalid")
        }
    }

    @Test
    fun `password error should be hidden if password is valid`() {
        val fragmentScenario = launchFragmentInContainer<EnrollmentFragment>()
        fragmentScenario.onFragment { fragment ->
            val passwordLayout = fragment.view!!.enrollment_password_layout

            passwordErrorLiveData.value = ValidatorResult.Failure

            assertThat(passwordLayout.error?.isNotEmpty()).isEqualTo(true)

            passwordErrorLiveData.value = ValidatorResult.Success

            assertThat(passwordLayout.error).isNull()
        }
    }

    @Test
    fun `show alert dialog when authentication fails`() {
        val fragmentScenario = launchFragment<EnrollmentFragment>(themeResId = R.style.Theme_Spinny)
        fragmentScenario.onFragment {
            enrollmentLiveData.value = Resource.Failure(Throwable())

            val alertDialog = ShadowDialog.getLatestDialog() as? AlertDialog
            assertThat(alertDialog).isNotNull()

            alertDialog as AlertDialog

            assertThat(alertDialog.isShowing).isEqualTo(true)

            val alertTitle = alertDialog.findViewById<TextView>(R.id.alertTitle)
            assertThat(alertTitle?.text.toString()).isEqualTo("Enrollment Failed")

            val alertMessage = alertDialog.findViewById<TextView>(android.R.id.message)
            assertThat(alertMessage?.text.toString()).isEqualTo("There was an issue enrolling user. Please try again.")

            val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            assertThat(positiveButton.text.toString()).isEqualTo("OK")
        }
    }
}
