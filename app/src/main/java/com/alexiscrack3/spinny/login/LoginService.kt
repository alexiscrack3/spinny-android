package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.models.LoginResponse
import com.alexiscrack3.spinny.models.LoginRequest
import com.alexiscrack3.spinny.models.Result
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/players/sign_in")
    fun signIn(@Body loginRequest: LoginRequest): Call<Result<LoginResponse>>?
}
