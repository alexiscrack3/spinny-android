package com.alexiscrack3.spinny.clubs.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
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
    fun `successful resource with clubs is emitted when getting clubs`() {
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

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data).isEqualTo(clubs)
    }

    @Test
    fun `failure resource is emitted when getting clubs`() {
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

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error).isEqualTo(throwable)
    }

    @Test
    fun `loading resource is emitted when getting clubs`() {
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
        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
    }
}
