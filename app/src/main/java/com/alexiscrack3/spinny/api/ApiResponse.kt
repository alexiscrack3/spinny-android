package com.alexiscrack3.spinny.api

data class ApiResponse<T>(
    val data: T,
    val errors: List<ApiError> = emptyList()
)
