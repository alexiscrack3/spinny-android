package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayerResponse
import retrofit2.http.GET

interface PlayersService {

    @GET("players/me")
    suspend fun getPlayer(): ApiResponse<PlayerResponse>
}
