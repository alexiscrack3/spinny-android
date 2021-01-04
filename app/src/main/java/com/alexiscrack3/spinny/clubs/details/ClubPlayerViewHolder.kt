package com.alexiscrack3.spinny.clubs.details

import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.databinding.ClubPlayerItemBinding
import com.alexiscrack3.spinny.models.Player
import kotlinx.android.synthetic.main.item_player.view.*

class ClubPlayerViewHolder(
    private val clubPlayerItemBinding: ClubPlayerItemBinding
) : RecyclerView.ViewHolder(clubPlayerItemBinding.root) {

    fun bind(player: Player, rank: Int) {
        with(clubPlayerItemBinding) {
            this.player = player

            itemView.player_rank_text_view.text = "$rank"

            this.executePendingBindings()
        }
    }
}
