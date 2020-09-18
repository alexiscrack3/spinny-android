package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.security.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
    private val securePreferences: SecurePreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = securePreferences.getAccessToken().orEmpty()
        val originalRequest = chain.request()
        val requestBuilder = originalRequest
            .newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
