package com.alexiscrack3.spinny.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ClubsService {
    @GET("/clubs")
    fun getClubs(): Call<ApiResponse<List<ClubData>>>?
}
