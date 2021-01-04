package com.alexiscrack3.spinny.clubs.list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.test
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.item_club.view.*
import org.junit.Before
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.anyString

class ClubViewHolderTest : SpinnyTest() {
    private val layoutInflater = LayoutInflater.from(context)
    private val clubItemBinding = ClubItemBinding.inflate(layoutInflater)
    private val glide by inject(RequestManager::class.java)
    private val requestBuilder = mock<RequestBuilder<Drawable>>()

    @Before
    override fun setUp() {
        super.setUp()
        declareMock<RequestManager> {
            given(this.load(anyString())).will { requestBuilder }
            given(requestBuilder.centerCrop()).will { requestBuilder }
        }
    }

    @Test
    fun `club is bound to view`() {
        val club = Club.test()
        val testObject = ClubViewHolder(clubItemBinding)

        testObject.bind(club)

        assertThat(clubItemBinding.club).isNotNull()
    }

    @Test
    fun `club image is loaded on view`() {
        val club = Club.test(imageUrl = "url")
        val testObject = ClubViewHolder(clubItemBinding)

        testObject.bind(club)

        verify(glide).load(club.imageUrl)
        verify(requestBuilder).centerCrop()
        verify(requestBuilder).into(clubItemBinding.clubAvatarImageView)
    }

    @Test
    fun `club name is bound to view`() {
        val club = Club.test(name = "name")
        val testObject = ClubViewHolder(clubItemBinding)
        val nameTextView = testObject.itemView.club_name_text_view

        testObject.bind(club)

        assertThat(nameTextView.text.toString()).isEqualTo(club.name)
    }

    @Test
    fun `number of members view should display 0 members when list is empty`() {
        val club = Club.test(membersCount = 0)
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = testObject.itemView.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("0 members")
    }

    @Test
    fun `number of members view should display 1 member when list contains one player`() {
        val club = Club.test(membersCount = 1)
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = testObject.itemView.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("1 member")
    }

    @Test
    fun `number of members view should display 2 members when list contains one player`() {
        val club = Club.test(membersCount = 2)
        val testObject = ClubViewHolder(clubItemBinding)
        val numberOfMembersTextView = testObject.itemView.club_number_of_members_text_view

        testObject.bind(club)

        assertThat(numberOfMembersTextView.text.toString()).isEqualTo("2 members")
    }

    @Test
    fun `pending bindings are executed when club is bound to view`() {
        val club = Club.test()
        val view = inflateView(R.layout.item_club)
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val testObject = ClubViewHolder(clubItemBinding)

        testObject.bind(club)

        verify(clubItemBinding).executePendingBindings()
    }

    @Test
    fun `navigate to club details screen when clicking on the view`() {
        val club = Club.test()
        val view = inflateView(R.layout.item_club)
        val clubItemBinding = mock<ClubItemBinding> {
            on { this.root } doReturn view
        }
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.main_nav_graph)
        }
        Navigation.setViewNavController(clubItemBinding.root, navController)
        val testObject = ClubViewHolder(clubItemBinding)
        testObject.bind(club)

        testObject.itemView.performClick()

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.club_fragment)
        assertThat(navController.backStack.last().arguments?.getString("CLUB_ID")).isEqualTo(club.id)
    }
}
