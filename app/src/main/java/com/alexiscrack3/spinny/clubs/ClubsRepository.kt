package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubsService
import com.alexiscrack3.spinny.api.models.ClubData
import com.alexiscrack3.spinny.models.Club
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubsRepository(
    private val clubsService: ClubsService,
    private val clubsMapper: ClubsMapper = ClubsMapper()
) {
    fun getClubById(id: Int, callback: (Result<Club?>) -> Unit) {
        clubsService.getClubById(id).enqueue(object :
            Callback<ApiResponse<ClubData?>> {
            override fun onResponse(
                call: Call<ApiResponse<ClubData?>>,
                apiResponse: Response<ApiResponse<ClubData?>>
            ) {
                if (apiResponse.isSuccessful) {
                    val clubsResponse = apiResponse.body()?.data
                    val club = clubsMapper.map(clubsResponse)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<ClubData?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getClubs(callback: (Result<List<Club>>) -> Unit) {
        clubsService.getClubs()?.enqueue(object :
            Callback<ApiResponse<List<ClubData>>?> {
            override fun onResponse(
                call: Call<ApiResponse<List<ClubData>>?>,
                apiResponse: Response<ApiResponse<List<ClubData>>?>
            ) {
                if (apiResponse.isSuccessful) {
                    val clubsResponse = apiResponse.body()?.data
                    if (clubsResponse == null) {
                        callback(Result.failure(Throwable("Something went wrong")))
                    } else {
                        val clubs = clubsMapper.map(clubsResponse)
                        callback(Result.success(clubs))
                    }
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<ClubData>>?>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }
}
