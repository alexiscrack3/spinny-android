package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubsResponse
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*

class ClubsMapperTest {

    @Test
    fun `map converts clubs response to list of clubs`() {
        val testObject = ClubsMapper()

        val clubResponseA = ClubsResponse(
            id = "1",
            name = "a",
            createdAt = Date(),
            imageUrl = "urla",
            membersCount = 0
        )
        val clubResponseB = ClubsResponse(
            id = "2",
            name = "b",
            createdAt = Date(),
            imageUrl = "urlb",
            membersCount = 0
        )
        val data = arrayOf(
            clubResponseA,
            clubResponseB
        )

        val actual = testObject.map(data)

        with(actual.first()) {
            assertThat(this.id).isEqualTo(clubResponseA.id)
            assertThat(this.name).isEqualTo(clubResponseA.name)
        }

        with(actual.last()) {
            assertThat(this.id).isEqualTo(clubResponseB.id)
            assertThat(this.name).isEqualTo(clubResponseB.name)
        }
    }
}
