package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.api.models.PlayerApiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/players/sign_in")
    fun signIn(@Body loginRequest: LoginRequest): Call<ApiResponse<PlayerApiModel>>?
}
