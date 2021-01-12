package com.alexiscrack3.spinny.clubs.list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import kotlinx.coroutines.launch

class ClubsViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {
    private val _clubsLiveData = MutableLiveData<Resource<List<Club>>>()

    val clubsLiveData: LiveData<Resource<List<Club>>>
        get() = _clubsLiveData

    fun getClubs() {
        viewModelScope.launch {
            try {
                _clubsLiveData.value = Resource.Loading()
                val clubs = clubsRepository.getClubs()
                _clubsLiveData.value = Resource.Success(clubs)
            } catch (t: Throwable) {
                _clubsLiveData.setValue(Resource.Failure(t))
            }
        }
    }

    fun onCreateClubClicked(view: View) {
        val directions = ClubsFragmentDirections.actionClubsFragmentToCreateClubFragment()
        view.findNavController().navigate(directions)
    }
}
