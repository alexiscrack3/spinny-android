package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.models.Club

class ClubsMapper {

    fun map(data: ClubResponse): Club {
        return Club(
            id = data.id,
            name = data.name,
            imageUrl = data.imageUrl,
            membersCount = data.membersCount
        ).apply {
            this.members = data.members
        }
    }

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
