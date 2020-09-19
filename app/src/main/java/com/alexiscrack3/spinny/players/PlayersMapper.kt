package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.models.Player

class PlayersMapper {

    fun map(data: PlayerResponse): Player {
        return with(data) {
            Player(
                id = this.id,
                email = this.email,
                firstName = this.firstName,
                lastName = this.lastName,
                rating = this.rating
            )
        }
    }
}
