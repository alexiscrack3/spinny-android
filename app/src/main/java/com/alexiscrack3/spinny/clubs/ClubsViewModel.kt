package com.alexiscrack3.spinny.clubs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.models.Club
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    val clubsState: MutableLiveData<List<Club>> = MutableLiveData()

    fun getClubs() {
        viewModelScope.launch {
            clubsRepository.getClubs { result: Result<List<Club>> ->
                if (result.isSuccess) {
                    clubsState.value = result.getOrDefault(emptyList())
                } else {
                    clubsState.value = emptyList()
                }
            }
        }
    }
}
