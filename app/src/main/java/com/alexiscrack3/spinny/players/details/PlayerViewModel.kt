package com.alexiscrack3.spinny.players.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.players.PlayersRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class PlayerViewModel(
    private val playersRepository: PlayersRepository
) : SpinnyViewModel() {
    private val _spinnerState = MutableLiveData<Boolean>()
    private val _nameState = MutableLiveData<String>()
    private val _ratingState = MutableLiveData<Int>()
    private val _profileErrorState = MutableLiveData<String>()

    val loadingState: MutableLiveData<Boolean>
        get() = _spinnerState

    val nameState: MutableLiveData<String>
        get() = _nameState

    val ratingState: MutableLiveData<Int>
        get() = _ratingState

    val profileErrorState: MutableLiveData<String>
        get() = _profileErrorState

    fun getPlayer() {
        viewModelScope.launch {
            try {
                _spinnerState.value = true

                val player = playersRepository.getPlayer()
                _nameState.value = "${player.firstName} ${player.lastName}"
                _ratingState.value = player.rating
            } catch (t: Throwable) {
                Timber.e(t)
                _profileErrorState.value = t.message
            } finally {
                _spinnerState.value = false
            }
        }
    }
}
