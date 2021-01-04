package com.alexiscrack3.spinny.clubs.details

import android.widget.LinearLayout
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.models.Player
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class ClubPlayersAdapterTest : SpinnyTest() {

    @Test
    fun `onCreateViewHolder should return view holder`() {
        val testObject = ClubPlayersAdapter()

        val viewHolder = testObject.onCreateViewHolder(LinearLayout(context), 0)

        assertThat(viewHolder).isInstanceOf(ClubPlayerViewHolder::class.java)
    }

    @Test
    fun `onBindViewHolder should bind item with view`() {
        val player = mock<Player>()
        val players = listOf(player)
        val viewHolder = mock<ClubPlayerViewHolder>()
        val testObject = ClubPlayersAdapter(players)

        testObject.onBindViewHolder(viewHolder, 0)

        verify(viewHolder).bind(player, 1)
    }

    @Test
    fun `itemCount should be initially 0`() {
        val testObject = ClubPlayersAdapter()
        assertThat(testObject.itemCount).isEqualTo(0)
    }

    @Test
    fun `itemCount should be equal to number of items`() {
        val player = mock<Player>()
        val players = listOf(player)
        val testObject = ClubPlayersAdapter(players)

        assertThat(testObject.itemCount).isEqualTo(players.size)
    }
}
