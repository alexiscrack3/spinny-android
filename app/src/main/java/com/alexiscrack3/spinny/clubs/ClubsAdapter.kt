package com.alexiscrack3.spinny.clubs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.databinding.ClubItemBinding
import com.alexiscrack3.spinny.models.Club

class ClubsAdapter(
    clubs: List<Club> = emptyList()
) : RecyclerView.Adapter<ClubViewHolder>() {
    private val clubs = clubs.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val binding = ClubItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClubViewHolder(binding)
    }

    override fun getItemCount() = clubs.size

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val club = clubs[position]
        holder.bind(club)
    }

    fun swap(clubs: List<Club>) {
        this.clubs.clear()
        this.clubs.addAll(clubs)
        notifyDataSetChanged()
    }
}
