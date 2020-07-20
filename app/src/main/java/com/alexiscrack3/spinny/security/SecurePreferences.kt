package com.alexiscrack3.spinny.security

import android.content.SharedPreferences

class SecurePreferences(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
    }

    fun setAccessToken(accessToken: String) {
        sharedPreferences
            .edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences
            .getString(ACCESS_TOKEN_KEY, null)
    }
}
