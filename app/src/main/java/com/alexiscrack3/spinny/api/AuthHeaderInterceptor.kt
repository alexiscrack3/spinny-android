package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.helpers.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader(
                "Authorization",
                tokenStore.accessToken
            )
            .build()

        val response = chain.proceed(request)
        val authorizationValue = response.headers().get("Authorization")
        val components = authorizationValue?.split(" ") ?: emptyList()
        val accessToken = components.lastOrNull()
        if (accessToken != null) {
            tokenStore.setAccessToken(accessToken)
        }

        return response
    }
}
