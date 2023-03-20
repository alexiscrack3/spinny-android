package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.api.models.ClubData
import retrofit2.Call
import retrofit2.http.GET

interface ClubsService {
    @GET("/clubs")
    fun getClubs(): Call<ApiResponse<List<ClubData>>>?
}
