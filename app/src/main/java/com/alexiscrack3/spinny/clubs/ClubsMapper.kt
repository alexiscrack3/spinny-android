package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.models.Club

class ClubsMapper {

    fun map(data: Array<ClubResponse>): List<Club> {
        return data.map { Club(it.id, it.name, it.membersCount) }
    }
}
