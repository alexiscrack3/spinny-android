package com.alexiscrack3.spinny.clubs.list

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alexiscrack3.spinny.CoroutinesTestRule
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class ClubsViewModelTest {
    private val clubsRepository = mock<ClubsRepository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `successful resource with clubs is emitted when getting clubs`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val club = Club.test()
        val clubs = listOf(club)
        whenever(clubsRepository.getClubs()).thenReturn(clubs)
        val testObject = ClubsViewModel(clubsRepository = clubsRepository)

        testObject.getClubs()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Resource.Success
        assertThat(actual.data).isEqualTo(clubs)
    }

    @Test
    fun `failure resource is emitted when getting clubs`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val exception = RuntimeException(Throwable())
        whenever(clubsRepository.getClubs()).thenThrow(exception)
        val testObject = ClubsViewModel(clubsRepository = clubsRepository)

        testObject.getClubs()

        val actual = testObject.clubsLiveData.getOrAwaitValue() as Resource.Failure
        assertThat(actual.error).isEqualTo(exception)
    }
//
//    @Test
//    fun `loading resource is emitted when getting clubs`() = coroutinesTestRule.testDispatcher.runBlockingTest {
//        whenever(clubsRepository.getClubs()).thenReturn(Single.never())
//        val testObject = ClubsViewModel(clubsRepository = clubsRepository)
//
//        testObject.getClubs()
//
//        val actual = testObject.clubsLiveData.getOrAwaitValue()
//        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
//    }
//
    @Test
    fun `navigate to create club screen when create is clicked`() {
        val testObject = ClubsViewModel(
            clubsRepository = clubsRepository
        )
        val navController = mock<NavController>()
        val view = mockk<View>()
        mockkStatic(Navigation::class)
        every { Navigation.findNavController(view) } returns navController

        testObject.onCreateClubClicked(view)

        verify(navController).navigate(ClubsFragmentDirections.actionClubsFragmentToCreateClubFragment())
    }
}
