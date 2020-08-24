package com.alexiscrack3.spinny.clubs.list

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.clubs.details.ClubFragment.Companion.CLUB_ID_KEY
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club
import kotlinx.android.synthetic.main.item_club.view.*

class ClubViewHolder(
    private val clubItemBinding: ClubItemBinding
) : RecyclerView.ViewHolder(clubItemBinding.root) {

    fun bind(club: Club) {
        with(clubItemBinding) {
            this.club = club

            val quantity = club.members.size
            itemView.club_number_of_members_text_view.text = itemView.context.resources.getQuantityString(
                R.plurals.number_of_members,
                quantity,
                quantity
            )

            // This forces the bindings to run immediately instead of delaying them until the next frame
            this.executePendingBindings()
        }
        itemView.setOnClickListener { view ->
            val args = bundleOf(CLUB_ID_KEY to club.id)
            view.findNavController().navigate(R.id.action_clubs_fragment_to_club_fragment, args)
        }
    }
}
