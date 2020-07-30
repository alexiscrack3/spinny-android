package com.alexiscrack3.spinny.clubs

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.clubs.details.ClubFragment.Companion.CLUB_ID_KEY
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club

class ClubViewHolder(
    private val clubItemBinding: ClubItemBinding
) : RecyclerView.ViewHolder(clubItemBinding.root) {

    fun bind(club: Club) {
        with(clubItemBinding) {
            this.club = club

            // This forces the bindings to run immediately instead of delaying them until the next frame
            this.executePendingBindings()
        }
        itemView.setOnClickListener { view ->
            val args = bundleOf(CLUB_ID_KEY to club.id)
            view.findNavController().navigate(R.id.action_clubsFragment_to_clubFragment, args)
        }
    }
}
