package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.helpers.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = if (request.method() == "POST" && request.url().uri().path.endsWith("/sign_in")) {
            val loginResponse = chain.proceed(request)
            val authorizationValue = loginResponse.headers().get("Authorization")
            val accessToken = authorizationValue?.substringAfter("Bearer ")
            if (accessToken != null) {
                tokenStore.setAccessToken(accessToken)
            }
            loginResponse
        } else {
            val newRequest = request.newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${tokenStore.accessToken}"
                )
                .build()
            chain.proceed(newRequest)
        }
        return response
    }
}
