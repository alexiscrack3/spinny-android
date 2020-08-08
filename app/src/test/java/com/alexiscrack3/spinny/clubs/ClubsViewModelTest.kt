package com.alexiscrack3.spinny.clubs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
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
        val testScheduler = TestScheduler()
        val testObject = ClubsViewModel(
            clubsRepository = clubsRepository,
            scheduler = testScheduler
        )

        testObject.getClubs()
        testScheduler.triggerActions()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Result.Success
        assertThat(actual.data).isEqualTo(clubs)
    }

    @Test
    fun `failing result is emitted when getting clubs`() {
        val throwable = Throwable()
        val clubsRepository = mock<ClubsRepository> {
            on { this.getClubs() } doReturn Single.error(throwable)
        }
        val testScheduler = TestScheduler()
        val testObject = ClubsViewModel(
            clubsRepository = clubsRepository,
            scheduler = testScheduler
        )

        testObject.getClubs()
        testScheduler.triggerActions()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Result.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading result is emitted when getting clubs`() {
        val clubsRepository = mock<ClubsRepository> {
            on { this.getClubs() } doReturn Single.never()
        }
        val testScheduler = TestScheduler()
        val testObject = ClubsViewModel(
            clubsRepository = clubsRepository,
            scheduler = testScheduler
        )

        testObject.getClubs()
        testScheduler.triggerActions()

        val actual = testObject.clubsLiveData.getOrAwaitValue()
        assertThat(actual).isInstanceOf(Result.Loading::class.java)
    }
}
