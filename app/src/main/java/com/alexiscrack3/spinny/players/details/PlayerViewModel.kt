package com.alexiscrack3.spinny.players.details

import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.players.PlayersRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PlayerViewModel(
    private val playersRepository: PlayersRepository,
    private val scheduler: Scheduler = Schedulers.io()
) : SpinnyViewModel() {
    private val _nameState = MutableLiveData<String>()
    val nameState: MutableLiveData<String> = _nameState

    fun getPlayer() {
        playersRepository.getPlayer()
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _nameState.postValue(it.id)
            }, {
                Timber.e(it)
            })
            .autoDispose()
    }
}
