package com.alexiscrack3.spinny.clubs.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.databinding.ClubPlayerItemBinding
import com.alexiscrack3.spinny.models.Player

class ClubPlayersAdapter(
    players: List<Player> = emptyList()
) : RecyclerView.Adapter<ClubPlayerViewHolder>() {
    private val players = players.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubPlayerViewHolder {
        val binding = ClubPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClubPlayerViewHolder(binding)
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: ClubPlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player, position + 1)
    }

    fun swap(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        notifyDataSetChanged()
    }
}
