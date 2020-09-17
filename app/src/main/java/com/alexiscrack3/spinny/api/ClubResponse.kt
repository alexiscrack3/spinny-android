package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class ClubResponse(
    @SerializedName("_id")
    val id: String,
    val name: String,
    @SerializedName("created_at")
    val createdAt: Date
)
