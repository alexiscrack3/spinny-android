package com.alexiscrack3.spinny.models

import com.google.gson.annotations.SerializedName

data class Player(
    val id: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val rating: Int
) {
    companion object
}
