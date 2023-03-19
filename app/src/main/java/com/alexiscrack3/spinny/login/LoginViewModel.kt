package com.alexiscrack3.spinny.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexiscrack3.spinny.PlayersMapper
import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.LoginResponse
import com.alexiscrack3.spinny.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    val playerState: MutableLiveData<Player?> = MutableLiveData()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.signIn(email, password)?.enqueue(object :
                Callback<ApiResponse<LoginResponse>?> {
                override fun onResponse(
                    call: Call<ApiResponse<LoginResponse>?>,
                    apiResponse: Response<ApiResponse<LoginResponse>?>
                ) {
                    if (apiResponse.isSuccessful) {
                        val loginResponse = apiResponse.body()?.data
                        if (loginResponse != null) {
                            val player = PlayersMapper().map(loginResponse)
                            this@LoginViewModel.playerState.value = player
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse<LoginResponse>?>, t: Throwable) {
                    println("error ${t.message}")
                }
            })
        }
    }
}
