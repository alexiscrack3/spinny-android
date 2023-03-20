package com.alexiscrack3.spinny.api.models

import com.google.gson.annotations.SerializedName

data class LoginData(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String
)
