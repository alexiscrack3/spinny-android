package com.alexiscrack3.spinny.api

sealed class Result<T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Loading<T>(val data: T? = null) : Result<T>()
    data class Failure<T>(val error: Throwable, val data: T? = null) : Result<T>()
}
