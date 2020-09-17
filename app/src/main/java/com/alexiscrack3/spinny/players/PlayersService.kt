package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.PlayerResponse
import io.reactivex.Single
import retrofit2.http.GET

interface PlayersService {

    @GET("players/me")
    fun getPlayer(): Single<ApiResponse<PlayerResponse>>
}
