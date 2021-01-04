package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ClubsMapperTest {

    @Test
    fun `map converts club response to club`() {
        val testObject = ClubsMapper()
        val clubResponse = ClubResponse.test()

        val actual = testObject.map(clubResponse)

        with(actual) {
            assertThat(this.id).isEqualTo(clubResponse.id)
            assertThat(this.name).isEqualTo(clubResponse.name)
            assertThat(this.imageUrl).isEqualTo(clubResponse.imageUrl)
            assertThat(this.membersCount).isEqualTo(clubResponse.membersCount)
        }
    }

    @Test
    fun `map converts clubs response to list of clubs`() {
        val testObject = ClubsMapper()
        val clubResponseA = ClubsResponse.test()
        val clubResponseB = ClubsResponse.test()
        val data = arrayOf(
            clubResponseA,
            clubResponseB
        )

        val actual = testObject.map(data)

        with(actual.first()) {
            assertThat(this.id).isEqualTo(clubResponseA.id)
            assertThat(this.name).isEqualTo(clubResponseA.name)
            assertThat(this.imageUrl).isEqualTo(clubResponseA.imageUrl)
            assertThat(this.membersCount).isEqualTo(clubResponseA.membersCount)
        }

        with(actual.last()) {
            assertThat(this.id).isEqualTo(clubResponseB.id)
            assertThat(this.name).isEqualTo(clubResponseB.name)
            assertThat(this.imageUrl).isEqualTo(clubResponseB.imageUrl)
            assertThat(this.membersCount).isEqualTo(clubResponseB.membersCount)
        }
    }
}
