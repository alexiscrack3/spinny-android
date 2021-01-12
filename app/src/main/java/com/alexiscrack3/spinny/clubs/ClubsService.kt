package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubsService {

    @GET("clubs")
    suspend fun getClubs(): ApiResponse<Array<ClubsResponse>>

    @GET("clubs/{id}")
    suspend fun getClubById(@Path("id") id: String): ApiResponse<ClubResponse>

    @POST("clubs")
    fun createClub(): Single<ApiResponse<ClubResponse>>
}
