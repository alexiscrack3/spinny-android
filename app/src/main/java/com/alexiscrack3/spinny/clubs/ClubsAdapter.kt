package com.alexiscrack3.spinny.clubs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.api.ClubData
import com.alexiscrack3.spinny.clubs.placeholder.PlaceholderContent.PlaceholderItem
import com.alexiscrack3.spinny.databinding.FragmentClubItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ClubsAdapter(
    private val clubs: MutableList<ClubData> = mutableListOf()
) : RecyclerView.Adapter<ClubsAdapter.ClubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = FragmentClubItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClubViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val item = clubs[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = clubs.size
    fun swap(clubs: List<ClubData>) {
        this.clubs.clear()
        this.clubs.addAll(clubs)
        this.notifyDataSetChanged()
    }

    inner class ClubViewHolder(binding: FragmentClubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
