package com.alexiscrack3.spinny.clubs

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ClubViewHolderTest : SpinnyTest() {
    private val view = inflateView(R.layout.item_club)

    @Test
    fun `club is bound to view`() {
        val club = Club("1", "name")
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)

        testObject.bind(club)

        verify(clubItemBinding).club = club
        verify(clubItemBinding).executePendingBindings()
    }

    @Test
    fun `navigate to club details screen when clicking on the view`() {
        val club = Club("1", "name")
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.main_nav_graph)
        }
        Navigation.setViewNavController(view, navController)
        val testObject = ClubViewHolder(clubItemBinding)
        testObject.bind(club)

        testObject.itemView.performClick()

        assertThat(
            navController.currentDestination?.id,
            equalTo(R.id.clubFragment)
        )

        assertThat(
            navController.backStack.last().arguments?.getString("CLUB_ID"),
            equalTo(club.id)
        )
    }
}
