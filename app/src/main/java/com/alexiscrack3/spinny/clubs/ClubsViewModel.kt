package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import io.reactivex.Single

class ClubsViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {

    fun getClubs(): Single<Response<Array<ClubResponse>>> {
        return clubsRepository.getClubs()
    }
}
