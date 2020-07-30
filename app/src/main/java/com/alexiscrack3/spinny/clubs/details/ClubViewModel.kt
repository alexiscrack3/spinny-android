package com.alexiscrack3.spinny.clubs.details

import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.clubs.ClubsRepository
import io.reactivex.Single

class ClubViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {

    fun getClubId(id: String): Single<Response<ClubResponse>> {
        return clubsRepository.getClubById(id)
    }
}
