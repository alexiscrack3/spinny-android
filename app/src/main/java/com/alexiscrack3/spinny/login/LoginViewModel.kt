package com.alexiscrack3.spinny.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.security.SecurePreferences
import com.alexiscrack3.spinny.validators.CompositeValidator
import com.alexiscrack3.spinny.validators.EmailFormatValidator
import com.alexiscrack3.spinny.validators.EmptyTextValidator
import com.alexiscrack3.spinny.validators.ValidatorResult

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val securePreferences: SecurePreferences
) : SpinnyViewModel() {
    private val _authenticationState = MutableLiveData<Resource<String>>()
    private val _emailErrorState = MutableLiveData<ValidatorResult>()
    private val _passwordErrorState = MutableLiveData<ValidatorResult>()

    val emailState = MutableLiveData<String>()
    val passwordState = MutableLiveData<String>()
    val emailErrorState: LiveData<ValidatorResult> = _emailErrorState
    val passwordErrorState: LiveData<ValidatorResult> = _passwordErrorState
    val authenticationState: LiveData<Resource<String>> = _authenticationState

    fun onSignInClicked() {
        val email = emailState.value.orEmpty()
        val password = passwordState.value.orEmpty()
        if (isFormValid(email, password)) {
            loginRepository.signIn(email, password)
                .doOnSubscribe {
                    _authenticationState.postValue(Resource.Loading())
                }
                .doOnSuccess {
                    securePreferences.setAccessToken(it.data.token)
                }
                .doOnSuccess {
                    emailState.postValue("")
                    passwordState.postValue("")
                }
                .subscribe({
                    _authenticationState.postValue(Resource.Success(it.data.token))
                }, {
                    _authenticationState.postValue(Resource.Failure(it))
                })
                .autoDispose()
        }
    }

    fun onSignUpClicked(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_enrollmentFragment)
    }

    private fun isEmailValid(email: String): Boolean {
        val validatorResult = CompositeValidator(EmailFormatValidator()).validate(email).also {
            _emailErrorState.value = it
        }
        return validatorResult == ValidatorResult.Success
    }

    private fun isPasswordValid(password: String): Boolean {
        val validatorResult = CompositeValidator(EmptyTextValidator()).validate(password).also {
            _passwordErrorState.value = it
        }
        return validatorResult == ValidatorResult.Success
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}
