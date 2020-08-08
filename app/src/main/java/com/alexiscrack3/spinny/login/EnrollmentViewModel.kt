package com.alexiscrack3.spinny.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.validators.CompositeValidator
import com.alexiscrack3.spinny.validators.EmailFormatValidator
import com.alexiscrack3.spinny.validators.EmptyTextValidator
import com.alexiscrack3.spinny.validators.ValidatorResult

class EnrollmentViewModel(
    private val loginRepository: LoginRepository,
    private val securePreferences: SecurePreferences
) : SpinnyViewModel() {
    private val _enrollmentState = MutableLiveData<Result<String>>()
    private val _emailErrorState = MutableLiveData<ValidatorResult>()
    private val _passwordErrorState = MutableLiveData<ValidatorResult>()

    val emailState = MutableLiveData<String>()
    val passwordState = MutableLiveData<String>()
    val emailErrorState: LiveData<ValidatorResult> = _emailErrorState
    val passwordErrorState: LiveData<ValidatorResult> = _passwordErrorState
    val enrollmentState: LiveData<Result<String>>
        get() = _enrollmentState

    fun onSignUpClicked() {
        val email = emailState.value.orEmpty()
        val password = passwordState.value.orEmpty()
        if (isFormValid(email, password)) {
            loginRepository.signUp(emailState.value.orEmpty(), passwordState.value.orEmpty())
                .doOnSubscribe {
                    _enrollmentState.postValue(Result.Loading())
                }
                .doOnSuccess {
                    securePreferences.setAccessToken(it.data.token)
                }
                .subscribe({
                    _enrollmentState.postValue(Result.Success(it.data.token))
                }, {
                    _enrollmentState.postValue(Result.Failure(it))
                })
                .autoDispose()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val validatorResult = CompositeValidator(EmailFormatValidator()).validate(email).also {
            _emailErrorState.value = it
        }
        return validatorResult == ValidatorResult.Valid
    }

    private fun isPasswordValid(password: String): Boolean {
        val validatorResult = CompositeValidator(EmptyTextValidator()).validate(password).also {
            _passwordErrorState.value = it
        }
        return validatorResult == ValidatorResult.Valid
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}
