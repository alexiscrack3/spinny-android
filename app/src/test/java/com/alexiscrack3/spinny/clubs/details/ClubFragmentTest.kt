package com.alexiscrack3.spinny.clubs.details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.utils.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock

class ClubFragmentTest : SpinnyTest() {
    private val clubViewModel by inject<ClubViewModel>()
    private val clubPlayersAdapter by inject<ClubPlayersAdapter>()
    private val clubLiveData = MutableLiveData<Resource<Club>>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<ClubViewModel>()
        whenever(clubViewModel.clubLiveData).doReturn(clubLiveData)
        declareMock<ClubPlayersAdapter>()
    }

    @Test
    fun `players are loaded on the list when getting successful result`() {
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.main_nav_graph)
        }
        val bundle = bundleOf("CLUB_ID" to "1")
        val fragmentScenario = launchFragment(bundle) {
            ClubFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }
            }
        }
        fragmentScenario.onFragment {
            val members = listOf(
                Player.test(),
                Player.test(),
                Player.test()
            )
            val club = Club.test().apply {
                this.members = members
            }
            clubLiveData.value = Resource.Success(club)

            verify(clubPlayersAdapter).swap(members)
        }
    }

    @Test
    fun `club is retrieved when screen is resumed`() {
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.main_nav_graph)
        }
        val id = "1"
        val bundle = bundleOf("CLUB_ID" to id)
        val fragmentScenario = launchFragment(bundle) {
            ClubFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }
            }
        }
        fragmentScenario.onFragment {
            verify(clubViewModel).getClubId(id)
        }
    }
}
