package com.rai.hearthstonecard.data.api

import android.content.Context
import com.rai.hearthstonecard.R


internal class TokenPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    companion object {
        const val USER_TOKEN = "user_token"
    }

}