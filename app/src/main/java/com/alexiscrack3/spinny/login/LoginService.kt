package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.SignInResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("auth/sign_in")
    fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>
}
