package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.models.ClubApiModel
import com.alexiscrack3.spinny.models.Club

class ClubsMapper {
    fun map(clubApiModel: ClubApiModel?): Club? {
        clubApiModel ?: return null

        return Club(
            id = clubApiModel.id,
            name = clubApiModel.name,
            description = clubApiModel.description
        )
    }

    fun map(clubApiModels: List<ClubApiModel>?): List<Club> {
        clubApiModels ?: return emptyList()

        return clubApiModels.map {
            Club(
                id = it.id,
                name = it.name,
                description = it.description
            )
        }
    }
}
