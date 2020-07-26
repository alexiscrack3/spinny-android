package com.alexiscrack3.spinny.clubs

import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club

class ClubViewHolder(
    private val clubItemBinding: ClubItemBinding
) : RecyclerView.ViewHolder(clubItemBinding.root) {

    fun bind(club: Club) {
        clubItemBinding.club = club
        // This forces the bindings to run immediately instead of delaying them until the next frame
        clubItemBinding.executePendingBindings()
    }
}
