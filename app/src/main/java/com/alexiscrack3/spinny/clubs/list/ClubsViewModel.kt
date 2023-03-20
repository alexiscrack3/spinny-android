package com.alexiscrack3.spinny.clubs.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    private val _clubsState: MutableLiveData<List<Club>> = MutableLiveData()
    val clubsState: LiveData<List<Club>>
        get() = _clubsState

    fun getClubs() {
        viewModelScope.launch {
            clubsRepository.getClubs { result: Result<List<Club>> ->
                if (result.isSuccess) {
                    _clubsState.value = result.getOrDefault(emptyList())
                } else {
                    _clubsState.value = emptyList()
                }
            }
        }
    }
}
