package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.api.models.PlayerApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayersService {
    @GET("/players/{id}")
    fun getPlayerById(@Path("id") id: Int): Call<ApiResponse<PlayerApiModel?>>
}
