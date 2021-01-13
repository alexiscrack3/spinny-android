package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayerResponse
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayersRepositoryTest {

    @Test
    fun `player should be retrieved from service`() = runBlockingTest {
        val id = "1"
        val email = "email@spinny.io"
        val firstName = "a"
        val lastName = "a"
        val rating = 1000
        val playerResponse = PlayerResponse.test(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            rating = rating
        )
        val player = Player(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            rating = rating
        )
        val playersService = mock<PlayersService> {
            onBlocking { this.getPlayer() } doReturn ApiResponse(playerResponse)
        }
        val testObject = PlayersRepository(
            playersService = playersService
        )

        val actual = testObject.getPlayer()

        assertThat(actual).isEqualTo(player)
    }
}
