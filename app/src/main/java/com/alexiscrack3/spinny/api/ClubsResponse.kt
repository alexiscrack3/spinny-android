package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class ClubsResponse(
    @SerializedName("_id")
    val id: String,
    val name: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("members_count")
    val membersCount: Int
) {
    companion object
}
