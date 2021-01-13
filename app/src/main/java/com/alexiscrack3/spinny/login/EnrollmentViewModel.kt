package com.alexiscrack3.spinny.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.validators.CompositeValidator
import com.alexiscrack3.spinny.validators.EmailFormatValidator
import com.alexiscrack3.spinny.validators.EmptyTextValidator
import com.alexiscrack3.spinny.validators.ValidatorResult
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EnrollmentViewModel(
    private val loginRepository: LoginRepository,
    private val securePreferences: SecurePreferences,
    private val scheduler: Scheduler = Schedulers.io()
) : SpinnyViewModel() {
    private val _enrollmentLiveData = MutableLiveData<Resource<String>>()
    private val _emailErrorLiveData = MutableLiveData<ValidatorResult>()
    private val _passwordErrorLiveData = MutableLiveData<ValidatorResult>()

    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()

    val emailErrorLiveData: LiveData<ValidatorResult>
        get() = _emailErrorLiveData

    val passwordErrorLiveData: LiveData<ValidatorResult>
        get() = _passwordErrorLiveData

    val enrollmentLiveData: LiveData<Resource<String>>
        get() = _enrollmentLiveData

    fun onSignUpClicked() {
        val email = emailLiveData.value.orEmpty()
        val password = passwordLiveData.value.orEmpty()
        if (isFormValid(email, password)) {
            loginRepository.signUp(email, password)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _enrollmentLiveData.value = Resource.Loading()
                }
                .doOnSuccess {
                    securePreferences.setAccessToken(it.data.token)
                }
                .subscribe({
                    _enrollmentLiveData.value = Resource.Success(it.data.token)
                }, {
                    _enrollmentLiveData.value = Resource.Failure(it)
                })
                .autoDispose()
        }
    }

    fun onSignInClicked(view: View) {
        view.findNavController().popBackStack()
    }

    private fun isEmailValid(email: String): Boolean {
        val validatorResult = CompositeValidator(EmailFormatValidator()).validate(email).also {
            _emailErrorLiveData.value = it
        }
        return validatorResult == ValidatorResult.Success
    }

    private fun isPasswordValid(password: String): Boolean {
        val validatorResult = CompositeValidator(EmptyTextValidator()).validate(password).also {
            _passwordErrorLiveData.value = it
        }
        return validatorResult == ValidatorResult.Success
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}
