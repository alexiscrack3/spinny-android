package com.alexiscrack3.spinny.clubs.list

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.item_club.view.*
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
    }

    @Test
    fun `number of members view should display 0 members when list is empty`() {
        val club = Club("1", "name").apply { members = emptyList() }
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = view.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("0 members")
    }

    @Test
    fun `number of members view should display 1 member when list contains one player`() {
        val club = Club("1", "name").apply { members = listOf("") }
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = view.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("1 member")
    }

    @Test
    fun `number of members view should display 2 members when list contains one player`() {
        val club = Club("1", "name").apply { members = listOf("", "") }
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = view.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("2 members")
    }

    @Test
    fun `pending bindings are executed when club is bound to view`() {
        val club = Club("1", "name")
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)

        testObject.bind(club)

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

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.club_fragment)
        assertThat(navController.backStack.last().arguments?.getString("CLUB_ID")).isEqualTo(club.id)
    }
}
