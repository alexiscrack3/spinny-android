package com.alexiscrack3.spinny.clubs.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.clubs.details.ClubFragment.Companion.CLUB_ID_KEY
import com.alexiscrack3.spinny.databinding.FragmentClubItemBinding
import com.alexiscrack3.spinny.models.Club

/**
 * [RecyclerView.Adapter] that can display a [Club].
 * TODO: Replace the implementation with code for your data type.
 */
class ClubsAdapter(
    clubs: List<Club> = emptyList()
) : RecyclerView.Adapter<ClubsAdapter.ClubViewHolder>() {
    private val clubs = clubs.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = FragmentClubItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClubViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val club = clubs[position]
        holder.bind(club)
    }

    override fun getItemCount(): Int = clubs.size

    fun swap(clubs: List<Club>) {
        this.clubs.clear()
        this.clubs.addAll(clubs)
        this.notifyDataSetChanged()
    }

    inner class ClubViewHolder(binding: FragmentClubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val idTextView: TextView = binding.idTextView
        private val nameTextView: TextView = binding.nameTextView

        override fun toString(): String {
            return super.toString() + " '" + nameTextView.text + "'"
        }

        fun bind(club: Club) {
            idTextView.text = club.id.toString()
            nameTextView.text = club.name
            itemView.setOnClickListener {
                val args = bundleOf(CLUB_ID_KEY to club.id)
                it.findNavController().navigate(R.id.action_clubsFragment_to_clubFragment, args)
            }
        }
    }
}
