package com.alexiscrack3.spinny.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class TokenStore(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)

    val accessToken: String
        get() = sharedPreferences.getString(
            ACCESS_TOKEN_KEY, "").orEmpty()

    companion object {
        const val ACCESS_TOKEN_KEY = "bearerToken"
        const val AUTH_PREFS = "auth_prefs"
        private const val PAYLOAD_INDEX = 1
    }

    fun setAccessToken(accessToken: String) {
        savePreferences(ACCESS_TOKEN_KEY to accessToken)
    }

    @SuppressLint("ApplySharedPref")
    private fun savePreferences(prefs: Pair<String, String>) {
        sharedPreferences.edit().putString(prefs.first, prefs.second).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun clearData() {
        sharedPreferences.edit().apply {
            remove(ACCESS_TOKEN_KEY)
            commit()
        }
    }
}
