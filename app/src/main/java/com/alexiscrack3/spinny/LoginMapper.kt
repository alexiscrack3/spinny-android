package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.api.models.ClubApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player

class LoginMapper {
    fun map(playerApiModel: PlayerApiModel): Player {
        return Player(
            id = playerApiModel.id,
            firstName = playerApiModel.firstName,
            lastName = playerApiModel.lastName,
            email = playerApiModel.email
        )
    }

    fun map(playerApiModels: List<PlayerApiModel>?): List<Player> {
        playerApiModels ?: return emptyList()

        return playerApiModels.map {
            Player(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
            )
        }
    }
}
