package com.alexiscrack3.spinny.clubs

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.models.Club

class ClubViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.club_name_text_view)

    fun bind(club: Club) {
        nameTextView.text = club.name
    }
}
