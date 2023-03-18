package com.alexiscrack3.spinny.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ClubsService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNSIsInNjcCI6InBsYXllciIsImF1ZCI6bnVsbCwiaWF0IjoxNjc5MTAzNzY4LCJleHAiOjE2ODAzOTk3NjgsImp0aSI6Ijk2NTFmMGM1LTVhMDQtNDkwNC05ZTIyLThhMzNlYjU2NmM1MSJ9.236YJAYD1q2MsnQZumDkKPkKzHwjv1-GM5XM92pcIlo")
    @GET("/clubs")
    fun getClubs(): Call<ApiResponse<List<ClubData>>>?
}
