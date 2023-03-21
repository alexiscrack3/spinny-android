package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Player

class PlayersMapper {
    fun map(playerApiModel: PlayerApiModel?): Player? {
        playerApiModel ?: return null

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
