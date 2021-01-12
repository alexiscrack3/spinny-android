package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.models.Player
import com.google.gson.annotations.SerializedName
import java.util.*

data class ClubResponse(
    @SerializedName("_id")
    val id: String,
    val name: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("image_url")
    val imageUrl: String,
    val members: List<Player>,
    @SerializedName("members_count")
    val membersCount: Int
) {
    companion object
}
