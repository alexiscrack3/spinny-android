package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.models.Player

class PlayersRepository(
    private val playersService: PlayersService,
    private val playersMapper: PlayersMapper = PlayersMapper()
) {

    suspend fun getPlayer(): Player {
        val response = playersService.getPlayer()
        return playersMapper.map(response.data)
    }
}
