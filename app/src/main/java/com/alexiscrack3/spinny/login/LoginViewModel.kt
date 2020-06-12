package com.alexiscrack3.spinny.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource

class LoginViewModel(
    private val loginRepository: LoginRepository
) : SpinnyViewModel() {
    private val _tokenLiveData = MutableLiveData<Resource<String>>(Resource.Success())
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val tokenLiveData: LiveData<Resource<String>>
        get() = _tokenLiveData

    fun onSignInClicked() {
        loginRepository.signIn(email.get().orEmpty(), password.get().orEmpty())
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
