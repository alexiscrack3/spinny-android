package com.alexiscrack3.spinny.clubs.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ClubsViewModel(
    private val clubsRepository: ClubsRepository,
    private val scheduler: Scheduler = Schedulers.io()
) : SpinnyViewModel() {
    private val _clubsLiveData = MutableLiveData<Resource<List<Club>>>()

    val clubsLiveData: LiveData<Resource<List<Club>>>
        get() = _clubsLiveData

    fun getClubs() {
        clubsRepository.getClubs()
            .subscribeOn(scheduler)
            .doOnSubscribe {
                _clubsLiveData.postValue(Resource.Loading())
            }
            .subscribe({
                _clubsLiveData.postValue(Resource.Success(it))
            }, {
                _clubsLiveData.postValue(Resource.Failure(it))
            })
            .autoDispose()
    }
}
