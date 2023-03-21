package com.alexiscrack3.spinny.clubs.members

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.databinding.FragmentClubMemberItemBinding
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.players.edit.PlayerEditFragment

/**
 * [RecyclerView.Adapter] that can display a [Player].
 * TODO: Replace the implementation with code for your data type.
 */
class ClubMembersAdapter(
    players: List<Player> = emptyList()
) : RecyclerView.Adapter<ClubMembersAdapter.ClubMemberViewHolder>() {
    private val players = players.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubMemberViewHolder {
        val view = FragmentClubMemberItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClubMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClubMemberViewHolder, position: Int) {
        val club = players[position]
        holder.bind(club)
    }

    override fun getItemCount(): Int = players.size

    fun swap(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        this.notifyDataSetChanged()
    }

    inner class ClubMemberViewHolder(binding: FragmentClubMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val idTextView: TextView = binding.idTextView
        private val emailTextView: TextView = binding.emailTextView

        override fun toString(): String {
            return super.toString() + " '" + emailTextView.text + "'"
        }

        fun bind(player: Player) {
            idTextView.text = player.id.toString()
            emailTextView.text = player.email
            itemView.setOnClickListener {
                val args = bundleOf(PlayerEditFragment.PLAYER_ID_KEY to player.id)
                it.findNavController()
                    .navigate(R.id.action_clubMembersFragment_to_playerEditFragment, args)
            }
        }
    }
}
