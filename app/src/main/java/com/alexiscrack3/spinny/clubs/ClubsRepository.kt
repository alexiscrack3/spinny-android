package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import io.reactivex.Single

class ClubsRepository(
    private val clubsService: ClubsService
) {

    fun getClubs(): Single<Response<Array<ClubResponse>>> {
        return clubsService.getClubs()
    }
}
