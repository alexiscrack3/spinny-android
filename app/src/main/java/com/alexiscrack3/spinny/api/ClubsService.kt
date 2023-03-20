package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.api.models.ClubApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClubsService {
    @GET("/clubs/{id}")
    fun getClubById(@Path("id") id: Int): Call<ApiResponse<ClubApiModel?>>

    @GET("/clubs")
    fun getClubs(): Call<ApiResponse<List<ClubApiModel>?>>
}
