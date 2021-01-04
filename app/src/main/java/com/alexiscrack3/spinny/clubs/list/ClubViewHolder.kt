package com.alexiscrack3.spinny.clubs.list

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.clubs.details.ClubFragment.Companion.CLUB_ID_KEY
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_club.view.*
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class ClubViewHolder(
    private val clubItemBinding: ClubItemBinding
) : RecyclerView.ViewHolder(clubItemBinding.root) {
    private val glide by inject(RequestManager::class.java) { parametersOf(itemView.context) }

    fun bind(club: Club) {
        with(clubItemBinding) {
            this.club = club

            glide
                .load(club.imageUrl)
                .centerCrop()
                .into(itemView.club_avatar_image_view)

            // This forces the bindings to run immediately instead of delaying them until the next frame
            this.executePendingBindings()
        }
        itemView.setOnClickListener { view ->
            val args = bundleOf(CLUB_ID_KEY to club.id)
            view.findNavController().navigate(R.id.action_clubs_fragment_to_club_fragment, args)
        }
    }
}
