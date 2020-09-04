package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubsService {

    @GET("clubs")
    fun getClubs(): Single<Response<Array<ClubResponse>>>

    @GET("clubs/{id}")
    fun getClubById(@Path("id") id: String): Single<Response<ClubResponse>>

    @POST("clubs")
    fun createClub(): Single<Response<ClubResponse>>
}
