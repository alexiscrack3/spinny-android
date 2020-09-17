package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.models.Player

class PlayersMapper {

    fun map(data: PlayerResponse): Player {
        return Player(
            id = data.id,
            name = "",
            lastName = "",
            rating = data.rating
        )
    }
}
