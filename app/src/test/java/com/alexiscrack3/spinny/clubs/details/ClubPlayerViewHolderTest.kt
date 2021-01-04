package com.alexiscrack3.spinny.clubs.details

import android.view.LayoutInflater
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.databinding.ClubPlayerItemBinding
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import kotlinx.android.synthetic.main.item_player.view.*
import org.junit.Test

class ClubPlayerViewHolderTest : SpinnyTest() {
    private val layoutInflater = LayoutInflater.from(context)
    private val clubPlayerItemBinding = ClubPlayerItemBinding.inflate(layoutInflater)

    @Test
    fun `player is bound to view`() {
        val player = Player.test()
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)

        testObject.bind(player, 0)

        assertThat(clubPlayerItemBinding.player).isNotNull()
    }

    @Test
    fun `player rank is bound to view`() {
        val player = Player.test()
        val rank = 1
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)
        val rankTextView = testObject.itemView.player_rank_text_view

        testObject.bind(player, rank)

        assertThat(rankTextView.text.toString()).isEqualTo(rank.toString())
    }

    @Test
    fun `player name is bound to view`() {
        val firstName = "foo"
        val lastName = "bar"
        val player = Player.test(
            firstName = firstName,
            lastName = lastName
        )
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)
        val nameTextView = testObject.itemView.player_name_text_view

        testObject.bind(player, 1)

        assertThat(nameTextView.text.toString()).isEqualTo("$firstName $lastName")
    }

    @Test
    fun `wins should be displayed in plural when number of wins is greater than 1`() {
        val player = Player.test()
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)
        val winsTextView = testObject.itemView.player_wins_text_view

        testObject.bind(player, 1)

        assertThat(winsTextView.text.toString()).isEqualTo("10 wins")
    }

    @Test
    fun `losses should be displayed in plural when number of losses is greater than 1`() {
        val player = Player.test()
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)
        val lossesTextView = testObject.itemView.player_losses_text_view

        testObject.bind(player, 1)

        assertThat(lossesTextView.text.toString()).isEqualTo("4 losses")
    }

    @Test
    fun `player rating is bound to view`() {
        val rating = 100
        val player = Player.test(rating = rating)
        val testObject = ClubPlayerViewHolder(clubPlayerItemBinding)
        val ratingTextView = testObject.itemView.player_rating_text_view

        testObject.bind(player, 1)

        assertThat(ratingTextView.text.toString()).isEqualTo(rating.toString())
    }
}
