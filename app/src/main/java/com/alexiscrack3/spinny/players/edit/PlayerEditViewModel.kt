package com.alexiscrack3.spinny.players.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.players.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerEditViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel() {
    private val _playerState: MutableLiveData<Player?> = MutableLiveData()
    val playerState: LiveData<Player?>
        get() = _playerState

    fun getPlayerById(id: Int) {
        viewModelScope.launch {
            playersRepository.getPlayerById(id) { result: Result<Player?> ->
                if (result.isSuccess) {
                    _playerState.value = result.getOrNull()
                } else {
                    _playerState.value = null
                }
            }
        }
    }
}
