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

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val securePreferences: SecurePreferences
) : SpinnyViewModel() {
    private val _tokenLiveData = MutableLiveData<Result<String>>()
    val emailLiveData = MutableLiveData<String>("asdf@gmail.com")
    val passwordLiveData = MutableLiveData<String>("asdf")

    private val _emailError = MutableLiveData<ValidatorResult>()
    val emailError: LiveData<ValidatorResult> = _emailError

    private val _passwordError = MutableLiveData<ValidatorResult>()
    val passwordError: LiveData<ValidatorResult> = _passwordError

    val tokenLiveData: LiveData<Result<String>>
        get() = _tokenLiveData

    fun onSignInClicked() {
        val email = emailLiveData.value.orEmpty()
        val password = passwordLiveData.value.orEmpty()
        if (isFormValid(email, password)) {
            loginRepository.signIn(email, password)
                .doOnSubscribe {
                    _tokenLiveData.postValue(Result.Loading())
                }
                .doOnSuccess {
                    securePreferences.setAccessToken(it.data.token)
                }
                .subscribe({
                    _tokenLiveData.postValue(Result.Success(it.data.token))
                }, {
                    _tokenLiveData.postValue(Result.Failure(it))
                })
                .autoDispose()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val validatorResult = CompositeValidator(EmailFormatValidator()).validate(email).also {
            _emailError.value = it
        }
        return validatorResult == ValidatorResult.Valid
    }

    private fun isPasswordValid(password: String): Boolean {
        val validatorResult = CompositeValidator(EmptyTextValidator()).validate(password).also {
            _passwordError.value = it
        }
        return validatorResult == ValidatorResult.Valid
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}
