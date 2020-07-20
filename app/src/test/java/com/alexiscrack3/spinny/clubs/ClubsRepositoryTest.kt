package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.models.Club
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test

class ClubsRepositoryTest {

    @Test
    fun `getClubs invokes getClubs on the login service`() {
        val id = "1"
        val name = "a"
        val clubResponseA = ClubResponse(id, name)
        val observable = Single.just(Response(arrayOf(clubResponseA)))
        val clubsService = mock<ClubsService> {
            on { this.getClubs() } doReturn observable
        }
        val expected = Club(
            id = id,
            name = name
        )
        val testObject = ClubsRepository(clubsService)

        testObject.getClubs()
            .test()
            .assertValue(listOf(expected))
    }
}
