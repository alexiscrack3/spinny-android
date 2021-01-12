package com.alexiscrack3.spinny.clubs.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import kotlinx.coroutines.launch

class ClubViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {
    private val _clubLiveData = MutableLiveData<Resource<Club>>()

    val clubLiveData: LiveData<Resource<Club>>
        get() = _clubLiveData

    fun getClubId(id: String) {
        viewModelScope.launch {
            try {
                _clubLiveData.value = Resource.Loading()
                val club = clubsRepository.getClubById(id)
                _clubLiveData.value = Resource.Success(club)
            } catch (t: Throwable) {
                _clubLiveData.value = Resource.Failure(t)
            }
        }
    }
}
