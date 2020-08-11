package com.alexiscrack3.spinny.clubs.list

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.clubs.ClubsRepository
import com.alexiscrack3.spinny.login.LoginViewModel
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test

class ClubsViewModelTest {
    private val clubsRepository = mock<ClubsRepository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `successful resource with clubs is emitted when getting clubs`() {
        val club = Club("1", "name")
        val clubs = listOf(club)
        whenever(clubsRepository.getClubs()).thenReturn(Single.just(clubs))
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
        whenever(clubsRepository.getClubs()).thenReturn(Single.error(throwable))
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
        whenever(clubsRepository.getClubs()).thenReturn(Single.never())
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

        verify(navController).navigate(R.id.action_clubsFragment_to_createClubFragment)
    }
}
