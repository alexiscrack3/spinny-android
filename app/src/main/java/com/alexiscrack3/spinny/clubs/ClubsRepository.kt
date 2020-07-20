package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.models.Club
import io.reactivex.Single

class ClubsRepository(
    private val clubsService: ClubsService,
    private val clubsMapper: ClubsMapper = ClubsMapper()
) {

    fun getClubs(): Single<List<Club>> {
        return clubsService.getClubs()
            .map { clubsMapper.map(it.data) }
    }
}
