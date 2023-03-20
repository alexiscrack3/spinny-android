package com.alexiscrack3.spinny.login

import androidx.lifecycle.LiveData
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
    private val _playerState: MutableLiveData<Player?> = MutableLiveData()
    val playerState: LiveData<Player?>
        get() = _playerState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.signIn(email, password) { result: Result<Player> ->
                if (result.isSuccess) {
                    _playerState.value = result.getOrNull()
                } else {
                    _playerState.value = null
                }
            }
        }
    }
}
