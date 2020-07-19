package com.alexiscrack3.spinny.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.validators.CompositeValidator
import com.alexiscrack3.spinny.validators.EmailFormatValidator
import com.alexiscrack3.spinny.validators.EmptyTextValidator
import com.alexiscrack3.spinny.validators.ValidatorResult

class LoginViewModel(
    private val loginRepository: LoginRepository
) : SpinnyViewModel() {
    private val _tokenLiveData = MutableLiveData<Resource<String>>()
    val emailLiveData = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()

    val passwordLiveData = MutableLiveData<String>()

    val tokenLiveData: LiveData<Resource<String>>
        get() = _tokenLiveData

    fun onSignInClicked() {
        val email = emailLiveData.value.orEmpty()
        val password = passwordLiveData.value.orEmpty()
        if (isFormValid(email, password)) {
            loginRepository.signIn(email, password)
                .doOnSubscribe {
                    _tokenLiveData.postValue(Resource.Loading())
                }
                .subscribe({
                    _tokenLiveData.postValue(Resource.Success(it.data.token))
                }, {
                    _tokenLiveData.postValue(Resource.Failure(it))
                })
                .autoDispose()
        }
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return CompositeValidator(EmailFormatValidator()).validate(email) == ValidatorResult.Valid
                && CompositeValidator(EmptyTextValidator()).validate(password) == ValidatorResult.Valid
    }
}
