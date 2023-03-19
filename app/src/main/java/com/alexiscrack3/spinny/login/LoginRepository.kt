package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginResponse
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.models.PlayerData
import retrofit2.Call

class LoginRepository(
    private val loginService: LoginService
) {
    fun signIn(email: String, password: String): Call<ApiResponse<LoginResponse>>? {
        val loginRequest = LoginRequest(
            player = PlayerData(
                email = email,
                password = password
            )
        )
        return loginService.signIn(loginRequest)
    }
}