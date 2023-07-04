package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.api.models.ClubApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubsService {
    @POST("clubs")
    fun createClub(@Body clubRequest: ClubRequest): Call<ApiDocument<ClubApiModel?>>

    @GET("clubs/{id}")
    fun getClubById(@Path("id") id: Int): Call<ApiDocument<ClubApiModel?>>

    @GET("clubs")
    fun getClubs(): Call<ApiDocument<List<ClubApiModel>?>>

    @DELETE("clubs/{id}")
    fun deleteClubById(@Path("id") id: Int): Call<ApiDocument<ClubApiModel?>>

    @GET("/clubs/{id}/members")
    fun getMembersByClubId(@Path("id") id: Int): Call<ApiDocument<List<PlayerApiModel>?>>
}
