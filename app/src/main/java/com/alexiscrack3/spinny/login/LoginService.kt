package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("auth/sign_in")
    fun signIn(@Body signInRequest: SignInRequest): Single<ApiResponse<SignInResponse>>

    @POST("auth/sign_up")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<ApiResponse<SignUpResponse>>
}
