package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName

data class ClubResponse(
    @SerializedName("_id")
    val id: String,
    val name: String
)
