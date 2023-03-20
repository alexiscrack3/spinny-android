package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.models.ClubData
import com.alexiscrack3.spinny.models.Club

class ClubsMapper {
    fun map(data: ClubData?): Club? {
        data ?: return null

        return Club(
            id = data.id,
            name = data.name
        )
    }

    fun map(clubsResponse: List<ClubData>): List<Club> {
        return clubsResponse.map {
            Club(
                id = it.id,
                name = it.name
            )
        }
    }
}
