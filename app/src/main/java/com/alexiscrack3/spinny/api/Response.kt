package com.alexiscrack3.spinny.api

data class Response<T>(
    val data: T,
    val errors: List<ApiError> = emptyList()
)
