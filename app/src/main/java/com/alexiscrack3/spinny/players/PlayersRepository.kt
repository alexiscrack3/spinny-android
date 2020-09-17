package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.models.Player
import io.reactivex.Single

class PlayersRepository(
    private val playersService: PlayersService,
    private val playersMapper: PlayersMapper = PlayersMapper()
) {

    fun getPlayer(): Single<Player> {
        return playersService.getPlayer()
            .map {
                playersMapper.map(it.data)
            }
    }
}
