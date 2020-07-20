package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.Response
import io.reactivex.Single
import retrofit2.http.GET

interface ClubsService {

    @GET("clubs")
    fun getClubs(): Single<Response<Array<ClubResponse>>>
}
