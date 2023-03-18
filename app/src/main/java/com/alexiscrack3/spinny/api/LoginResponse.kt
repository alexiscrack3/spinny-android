package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String
)
