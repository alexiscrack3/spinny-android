package com.alexiscrack3.spinny.clubs.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.models.Club
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock

class ClubsFragmentTest : SpinnyTest() {
    private val clubsViewModel by inject<ClubsViewModel>()
    private val clubsAdapter by inject<ClubsAdapter>()
    private val clubsLiveData = MutableLiveData<Resource<List<Club>>>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<ClubsViewModel>()
        whenever(clubsViewModel.clubsLiveData).thenReturn(clubsLiveData)
        declareMock<ClubsAdapter>()
    }

    @Test
    fun `clubs are loaded on screen when getting successful result`() {
        val fragmentScenario = launchFragmentInContainer<ClubsFragment>()
        fragmentScenario.onFragment {
            val club = Club(
                id = "1",
                name = "name"
            )
            val clubs = listOf(club)
            clubsLiveData.value = Resource.Success(clubs)

            verify(clubsAdapter).swap(clubs)
        }
    }
}
