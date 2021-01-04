package com.alexiscrack3.spinny.models

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("_id")
    val id: String,
    val email: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val rating: Int
) {
    companion object
}
