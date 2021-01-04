package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.models.Club

class ClubsMapper {

    fun map(data: Array<ClubsResponse>): List<Club> {
        return data.map {
            Club(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl,
                membersCount = it.membersCount
            )
        }
    }
}
