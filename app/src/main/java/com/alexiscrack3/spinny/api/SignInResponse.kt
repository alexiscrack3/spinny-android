package com.alexiscrack3.spinny.api

data class SignInResponse(
    val user: PlayerResponse,
    val token: String
)
