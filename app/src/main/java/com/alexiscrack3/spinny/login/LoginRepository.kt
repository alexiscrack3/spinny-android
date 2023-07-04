package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ApiDocument
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.api.models.PlayerAccountApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.players.PlayersMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(
    private val loginService: LoginService,
    private val playersMapper: PlayersMapper = PlayersMapper()
) {
    fun signIn(email: String, password: String, callback: (Result<Player>) -> Unit) {
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        loginService.signIn(loginRequest).enqueue(object :
            Callback<ApiDocument<PlayerApiModel>?> {
            override fun onResponse(
                call: Call<ApiDocument<PlayerApiModel>?>,
                response: Response<ApiDocument<PlayerApiModel>?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        callback(Result.failure(Throwable("Something went wrong")))
                    } else {
                        val player = playersMapper.map(data)!!
                        callback(Result.success(player))
                    }
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<PlayerApiModel>?>, t: Throwable) {
                println("error ${t.message}")
                callback(Result.failure(t))
            }
        })
    }
}
