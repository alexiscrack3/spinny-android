package com.alexiscrack3.spinny.clubs.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.CoroutinesTestRule
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class ClubViewModelTest {
    private val clubsRepository = mock<ClubsRepository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `successful resource with clubs is emitted when getting clubs`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val club = Club.test()
        whenever(clubsRepository.getClubById(club.id)).thenReturn(club)
        val testObject = ClubViewModel(clubsRepository = clubsRepository)

        testObject.getClubId(club.id)

        val actual = testObject.clubLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data).isEqualTo(club)
    }

    @Test
    fun `failure resource is emitted when getting clubs`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val id = "1"
        val exception = RuntimeException(Throwable())
        whenever(clubsRepository.getClubById(id)).thenThrow(exception)
        val testScheduler = TestScheduler()
        val testObject = ClubViewModel(clubsRepository = clubsRepository)

        testObject.getClubId(id)
        testScheduler.triggerActions()

        val actual = testObject.clubLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error).isEqualTo(exception)
    }

//    @Test
//    fun `loading resource is emitted when getting clubs`() {
//        val id = "1"
//        whenever(clubsRepository.getClubById(id)).thenReturn(Single.never())
//        val testObject = ClubViewModel(clubsRepository = clubsRepository)
//
//        testObject.getClubId(id)
//
//        val actual = testObject.clubLiveData.getOrAwaitValue()
//        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
//    }
}
