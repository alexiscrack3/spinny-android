package com.alexiscrack3.spinny.clubs.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ClubViewModel(
    private val clubsRepository: ClubsRepository,
    private val scheduler: Scheduler = Schedulers.io()
) : SpinnyViewModel() {
    private val _clubLiveData = MutableLiveData<Resource<Club>>()

    val clubLiveData: LiveData<Resource<Club>>
        get() = _clubLiveData

    fun getClubId(id: String) {
        clubsRepository.getClubById(id)
            .subscribeOn(scheduler)
            .doOnSubscribe {
                _clubLiveData.postValue(Resource.Loading())
            }
            .subscribe({
                _clubLiveData.postValue(Resource.Success(it))
            }, {
                _clubLiveData.postValue(Resource.Failure(it))
            })
            .autoDispose()
    }
}
