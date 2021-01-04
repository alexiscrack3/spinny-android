package com.alexiscrack3.spinny.clubs.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import com.alexiscrack3.spinny.ShadowSwipeRefreshLayout
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.android.synthetic.main.fragment_clubs.*
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow

@Config(shadows = [ShadowSwipeRefreshLayout::class])
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
    fun `clubs are loaded on the screen when getting successful result`() {
        val fragmentScenario = launchFragmentInContainer<ClubsFragment>()
        fragmentScenario.onFragment {
            val club = Club.test()
            val clubs = listOf(club)
            clubsLiveData.value = Resource.Success(clubs)

            verify(clubsAdapter).swap(clubs)
        }
    }

    @Test
    fun `clubs are reloaded on the screen when getting successful result`() {
        val fragmentScenario = launchFragmentInContainer<ClubsFragment>()
        fragmentScenario.onFragment { fragment ->
            val shadowRefreshLayout = Shadow.extract<ShadowSwipeRefreshLayout>(fragment.clubs_swipe_refresh_layout)
            shadowRefreshLayout.onRefreshListener.onRefresh()

            verify(clubsViewModel, times(2)).getClubs()
        }
    }

    @Test
    fun `swipe refresh should be hidden when getting successful result`() {
        val fragmentScenario = launchFragmentInContainer<ClubsFragment>()
        fragmentScenario.onFragment { fragment ->
            clubsLiveData.value = Resource.Success()

            val actual = fragment.clubs_swipe_refresh_layout.isRefreshing

            assertThat(actual).isFalse()
        }
    }

    @Test
    fun `swipe refresh should be hidden when getting failing result`() {
        val fragmentScenario = launchFragmentInContainer<ClubsFragment>()
        fragmentScenario.onFragment { fragment ->
            clubsLiveData.value = Resource.Failure(Throwable())

            val actual = fragment.clubs_swipe_refresh_layout.isRefreshing

            assertThat(actual).isFalse()
        }
    }
}
