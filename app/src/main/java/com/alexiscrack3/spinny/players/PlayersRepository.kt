package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayersService
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayersRepository(
    private val playersService: PlayersService,
    private val playersMapper: PlayersMapper = PlayersMapper()
) {

    fun getPlayerById(id: Int, callback: (Result<Player?>) -> Unit) {
        playersService.getPlayerById(id).enqueue(object :
            Callback<ApiResponse<PlayerApiModel?>> {
            override fun onResponse(
                call: Call<ApiResponse<PlayerApiModel?>>,
                apiResponse: Response<ApiResponse<PlayerApiModel?>>
            ) {
                if (apiResponse.isSuccessful) {
                    val data = apiResponse.body()?.data
                    val player = playersMapper.map(data)
                    callback(Result.success(player))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<PlayerApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }
}
