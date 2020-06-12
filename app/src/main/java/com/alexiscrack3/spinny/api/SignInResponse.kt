package com.alexiscrack3.spinny.api

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val rating: Int
)

data class UserResponse(
    val user: PlayerResponse,
    val token: String
)

data class SignInResponse(
    val data: UserResponse
)
