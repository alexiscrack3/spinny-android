package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubRequest
import com.alexiscrack3.spinny.api.ClubsService
import com.alexiscrack3.spinny.api.models.ClubApiModel
import com.alexiscrack3.spinny.models.Club
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubsRepository(
    private val clubsService: ClubsService,
    private val clubsMapper: ClubsMapper = ClubsMapper()
) {
    fun createClub(name: String, description: String?, callback: (Result<Club?>) -> Unit) {
        val clubRequest = ClubRequest(
            name = name,
            description = description
        )
        clubsService.createClub(clubRequest).enqueue(object :
            Callback<ApiResponse<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiResponse<ClubApiModel?>>,
                apiResponse: Response<ApiResponse<ClubApiModel?>>
            ) {
                if (apiResponse.isSuccessful) {
                    val clubsResponse = apiResponse.body()?.data
                    val club = clubsMapper.map(clubsResponse)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun deleteClubById(id: Int, callback: (Result<Club?>) -> Unit) {
        clubsService.deleteClubById(id).enqueue(object :
            Callback<ApiResponse<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiResponse<ClubApiModel?>>,
                apiResponse: Response<ApiResponse<ClubApiModel?>>
            ) {
                if (apiResponse.isSuccessful) {
                    val clubsResponse = apiResponse.body()?.data
                    val club = clubsMapper.map(clubsResponse)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getClubById(id: Int, callback: (Result<Club?>) -> Unit) {
        clubsService.getClubById(id).enqueue(object :
            Callback<ApiResponse<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiResponse<ClubApiModel?>>,
                apiResponse: Response<ApiResponse<ClubApiModel?>>
            ) {
                if (apiResponse.isSuccessful) {
                    val clubsResponse = apiResponse.body()?.data
                    val club = clubsMapper.map(clubsResponse)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getClubs(callback: (Result<List<Club>>) -> Unit) {
        clubsService.getClubs()?.enqueue(object :
            Callback<ApiResponse<List<ClubApiModel>?>> {
            override fun onResponse(
                call: Call<ApiResponse<List<ClubApiModel>?>>,
                apiResponse: Response<ApiResponse<List<ClubApiModel>?>>
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

            override fun onFailure(call: Call<ApiResponse<List<ClubApiModel>?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }
}
