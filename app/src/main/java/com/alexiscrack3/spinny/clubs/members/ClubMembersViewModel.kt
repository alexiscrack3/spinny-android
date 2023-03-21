package com.alexiscrack3.spinny.clubs.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubMembersViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    private val _membersState: MutableLiveData<List<Player>> = MutableLiveData()
    val membersState: LiveData<List<Player>>
        get() = _membersState

    fun getMembersByClubId(id: Int) {
        viewModelScope.launch {
            clubsRepository.getMembersByClubId(id) { result: Result<List<Player>> ->
                if (result.isSuccess) {
                    _membersState.value = result.getOrDefault(emptyList())
                } else {
                    _membersState.value = emptyList()
                }
            }
        }
    }
}
