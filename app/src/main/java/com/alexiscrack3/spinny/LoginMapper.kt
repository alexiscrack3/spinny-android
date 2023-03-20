package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.api.models.PlayerApiModel
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
}
