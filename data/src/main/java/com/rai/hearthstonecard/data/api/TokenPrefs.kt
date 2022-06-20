package com.rai.hearthstonecard.data.api

import android.content.Context

internal class TokenPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit()
        .putString(USER_TOKEN, token)
        .apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    companion object {
        private const val USER_TOKEN = "user_token"
    }

}