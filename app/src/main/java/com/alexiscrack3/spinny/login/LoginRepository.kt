package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.LoginMapper
import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.models.LoginData
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.api.models.PlayerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(
    private val loginService: LoginService,
    private val loginMapper: LoginMapper = LoginMapper()
) {
    fun signIn(email: String, password: String, callback: (Result<Player>) -> Unit) {
        val loginRequest = LoginRequest(
            player = PlayerData(
                email = email,
                password = password
            )
        )
        loginService.signIn(loginRequest)?.enqueue(object :
            Callback<ApiResponse<LoginData>?> {
            override fun onResponse(
                call: Call<ApiResponse<LoginData>?>,
                apiResponse: Response<ApiResponse<LoginData>?>
            ) {
                if (apiResponse.isSuccessful) {
                    val loginResponse = apiResponse.body()?.data
                    if (loginResponse == null) {
                        callback(Result.failure(Throwable("Something went wrong")))
                    } else {
                        val player = loginMapper.map(loginResponse)
                        callback(Result.success(player))
                    }
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<LoginData>?>, t: Throwable) {
                println("error ${t.message}")
                callback(Result.failure(t))
            }
        })
    }
}
