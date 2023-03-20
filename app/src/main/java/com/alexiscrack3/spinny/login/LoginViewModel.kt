package com.alexiscrack3.spinny.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    val playerState: MutableLiveData<Player?> = MutableLiveData()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.signIn(email, password) { result: Result<Player> ->
                if (result.isSuccess) {
                    playerState.value = result.getOrNull()
                } else {
                    playerState.value = null
                }
            }
        }
    }
}
