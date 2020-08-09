package com.alexiscrack3.spinny.clubs.list

import android.widget.LinearLayout
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.models.Club
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class ClubsAdapterTest : SpinnyTest() {

    @Test
    fun `onCreateViewHolder returns view holder`() {
        val testObject = ClubsAdapter()

        val viewHolder = testObject.onCreateViewHolder(LinearLayout(context), 0)

        assertThat(viewHolder).isInstanceOf(ClubViewHolder::class.java)
    }

    @Test
    fun `onBindViewHolder binds correct item`() {
        val club = mock<Club>()
        val clubs = listOf(club)
        val viewHolder = mock<ClubViewHolder>()
        val testObject = ClubsAdapter(clubs)

        testObject.onBindViewHolder(viewHolder, 0)

        verify(viewHolder).bind(eq(club))
    }

    @Test
    fun `count is initially 0`() {
        val testObject = ClubsAdapter()
        assertThat(testObject.itemCount).isEqualTo(0)
    }

    @Test
    fun `count is equal to number of items`() {
        val club = mock<Club>()
        val clubs = listOf(club)
        val testObject = ClubsAdapter(clubs)

        assertThat(testObject.itemCount).isEqualTo(clubs.size)
    }
}
