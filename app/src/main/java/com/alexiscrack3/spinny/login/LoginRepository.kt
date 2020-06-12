package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.SignInResponse
import io.reactivex.Single

class LoginRepository(
    private val loginService: LoginService
) {
    fun signIn(email: String, password: String): Single<SignInResponse> {
        return loginService.signIn(SignInRequest(email, password))
    }
}
