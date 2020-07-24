package com.alexiscrack3.spinny.clubs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class ClubsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `successful result with clubs is emitted when getting clubs`() {
        val club = Club("1", "name")
        val clubs = listOf(club)
        val clubsRepository = mock<ClubsRepository> {
            on { this.getClubs() } doReturn Single.just(clubs)
        }
        val testObject = ClubsViewModel(clubsRepository)

        testObject.getClubs()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Result.Success
        assertThat(actual.data, equalTo(clubs))
    }

    @Test
    fun `failing result is emitted when getting clubs`() {
        val throwable = Throwable()
        val clubsRepository = mock<ClubsRepository> {
            on { this.getClubs() } doReturn Single.error(throwable)
        }
        val testObject = ClubsViewModel(clubsRepository)

        testObject.getClubs()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Result.Failure
        assertThat(actual.error, equalTo(throwable))
    }

    @Test
    fun `loading result is emitted when getting clubs`() {
        val clubsRepository = mock<ClubsRepository> {
            on { this.getClubs() } doReturn Single.never()
        }
        val testObject = ClubsViewModel(clubsRepository)

        testObject.getClubs()

        val actual = testObject.clubsLiveData.getOrAwaitValue()
        assertThat(actual, instanceOf(Result.Loading::class.java))
    }
}
