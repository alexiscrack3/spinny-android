package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.models.Player
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
import java.util.*

class PlayersRepositoryTest {

    @Test
    fun `player should be retrieved from service`() {
        val id = "1"
        val email = "email@spinny.io"
        val firstName = "a"
        val lastName = "a"
        val rating = 1000
        val playerResponse = PlayerResponse(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            rating = rating,
            createdAt = Date()
        )
        val player = Player(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            rating = rating
        )
        val playersService = mock<PlayersService> {
            on { this.getPlayer() } doReturn Single.just(ApiResponse(playerResponse))
        }
        val testObject = PlayersRepository(playersService)

        testObject.getPlayer()
            .test()
            .assertValue(player)
    }
}
