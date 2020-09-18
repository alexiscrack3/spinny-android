package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class PlayerResponse(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val rating: Int,
    @SerializedName("created_at")
    val createdAt: Date
)
