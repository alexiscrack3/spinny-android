package com.alexiscrack3.spinny.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.models.Club

class ClubsViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {
    private val _clubsLiveData = MutableLiveData<Result<List<Club>>>()

    val clubsLiveData: LiveData<Result<List<Club>>>
        get() = _clubsLiveData

    fun getClubs() {
        clubsRepository.getClubs()
            .doOnSubscribe {
                _clubsLiveData.postValue(Result.Loading())
            }
            .subscribe({
                _clubsLiveData.postValue(Result.Success(it))
            }, {
                _clubsLiveData.postValue(Result.Failure(it))
            })
            .autoDispose()
    }
}
