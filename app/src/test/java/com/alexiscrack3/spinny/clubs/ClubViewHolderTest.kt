package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.models.Club
import kotlinx.android.synthetic.main.item_club.view.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ClubViewHolderTest : SpinnyTest() {
    private val view = inflateView(R.layout.item_club)

    @Test
    fun `when bound title text is set`() {
        val club = Club("1", "name")
        val testObject = ClubViewHolder(view)

        testObject.bind(club)

        assertThat(view.club_name_text_view.text.toString(), equalTo(club.name))
    }
}
