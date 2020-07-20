package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.Response
import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.UserResponse
import io.reactivex.Single

class LoginRepository(
    private val loginService: LoginService
) {

    fun signIn(email: String, password: String): Single<Response<UserResponse>> {
        return loginService.signIn(SignInRequest(email, password))
    }
}
