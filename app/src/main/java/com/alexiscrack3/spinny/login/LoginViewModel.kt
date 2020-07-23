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
    val emailLiveData = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()

    val passwordLiveData = MutableLiveData<String>()

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

    private fun isFormValid(email: String, password: String): Boolean {
        return CompositeValidator(EmailFormatValidator()).validate(email) == ValidatorResult.Valid
                && CompositeValidator(EmptyTextValidator()).validate(password) == ValidatorResult.Valid
    }
}
