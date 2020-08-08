package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.*
import io.reactivex.Single

class LoginRepository(
    private val loginService: LoginService
) {

    fun signIn(email: String, password: String): Single<Response<SignInResponse>> {
        return loginService.signIn(SignInRequest(email, password))
    }

    fun signUp(email: String, password: String): Single<Response<SignUpResponse>> {
        return loginService.signUp(SignUpRequest(email, password))
    }
}
