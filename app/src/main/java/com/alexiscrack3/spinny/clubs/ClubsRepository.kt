package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.players.PlayersMapper
import com.alexiscrack3.spinny.api.ApiDocument
import com.alexiscrack3.spinny.api.ClubRequest
import com.alexiscrack3.spinny.api.ClubsService
import com.alexiscrack3.spinny.api.models.ClubApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubsRepository(
    private val clubsService: ClubsService,
    private val clubsMapper: ClubsMapper = ClubsMapper(),
    private val playersMapper: PlayersMapper = PlayersMapper()
) {
    fun createClub(name: String, description: String?, callback: (Result<Club?>) -> Unit) {
        val clubRequest = ClubRequest(
            name = name,
            description = description
        )
        clubsService.createClub(clubRequest).enqueue(object :
            Callback<ApiDocument<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiDocument<ClubApiModel?>>,
                response: Response<ApiDocument<ClubApiModel?>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    val club = clubsMapper.map(data)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun deleteClubById(id: Int, callback: (Result<Club?>) -> Unit) {
        clubsService.deleteClubById(id).enqueue(object :
            Callback<ApiDocument<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiDocument<ClubApiModel?>>,
                response: Response<ApiDocument<ClubApiModel?>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    val club = clubsMapper.map(data)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getClubById(id: Int, callback: (Result<Club?>) -> Unit) {
        clubsService.getClubById(id).enqueue(object :
            Callback<ApiDocument<ClubApiModel?>> {
            override fun onResponse(
                call: Call<ApiDocument<ClubApiModel?>>,
                response: Response<ApiDocument<ClubApiModel?>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    val club = clubsMapper.map(data)
                    callback(Result.success(club))
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<ClubApiModel?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getClubs(callback: (Result<List<Club>>) -> Unit) {
        clubsService.getClubs()?.enqueue(object :
            Callback<ApiDocument<List<ClubApiModel>?>> {
            override fun onResponse(
                call: Call<ApiDocument<List<ClubApiModel>?>>,
                response: Response<ApiDocument<List<ClubApiModel>?>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        callback(Result.failure(Throwable("Something went wrong")))
                    } else {
                        val clubs = clubsMapper.map(data)
                        callback(Result.success(clubs))
                    }
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<List<ClubApiModel>?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }

    fun getMembersByClubId(id: Int, callback: (Result<List<Player>>) -> Unit) {
        clubsService.getMembersByClubId(id).enqueue(object :
            Callback<ApiDocument<List<PlayerApiModel>?>> {
            override fun onResponse(
                call: Call<ApiDocument<List<PlayerApiModel>?>>,
                response: Response<ApiDocument<List<PlayerApiModel>?>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data == null) {
                        callback(Result.failure(Throwable("Something went wrong")))
                    } else {
                        val players = playersMapper.map(data)
                        callback(Result.success(players))
                    }
                } else {
                    callback(Result.failure(Throwable("Something went wrong")))
                }
            }

            override fun onFailure(call: Call<ApiDocument<List<PlayerApiModel>?>>, t: Throwable) {
                print(t.message)
                callback(Result.failure(t))
            }
        })
    }
}
