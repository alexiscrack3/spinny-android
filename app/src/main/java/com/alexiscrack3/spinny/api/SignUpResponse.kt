package com.alexiscrack3.spinny.api

data class SignUpResponse(
    val user: PlayerResponse,
    val token: String
)
