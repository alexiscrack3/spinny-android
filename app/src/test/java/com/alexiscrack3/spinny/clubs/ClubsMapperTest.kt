package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test

class ClubsMapperTest {

    @Test
    fun `map converts clubs response to list of clubs`() {
        val testObject = ClubsMapper()

        val clubResponseA = ClubResponse(
            id = "1",
            name = "a"
        )
        val clubResponseB = ClubResponse(
            id = "2",
            name = "b"
        )
        val data = arrayOf(
            clubResponseA,
            clubResponseB
        )

        val actual = testObject.map(data)

        with(actual.first()) {
            assertThat(this.id, equalTo(clubResponseA.id))
            assertThat(this.name, equalTo(clubResponseA.name))
        }

        with(actual.last()) {
            assertThat(this.id, equalTo(clubResponseB.id))
            assertThat(this.name, equalTo(clubResponseB.name))
        }
    }
}
