package com.rai.hearthstonecard.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TokenResponse(
    @SerializedName("access_token")
    @Expose val accessToken: String,

    @SerializedName("token_type")
    @Expose val tokenType: String,
)