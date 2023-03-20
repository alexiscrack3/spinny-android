package com.alexiscrack3.spinny.clubs.create

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
class CreateClubViewModel @Inject constructor(
    private val clubsRepository: ClubsRepository
) : ViewModel() {
    private val _clubState: MutableLiveData<Club?> = MutableLiveData()
    val clubState: LiveData<Club?>
        get() = _clubState

    fun createClub(name: String, description: String?) {
        viewModelScope.launch {
            clubsRepository.createClub(name, description) { result: Result<Club?> ->
                if (result.isSuccess) {
                    _clubState.value = result.getOrNull()
                } else {
                    _clubState.value = null
                }
            }
        }
    }
}
