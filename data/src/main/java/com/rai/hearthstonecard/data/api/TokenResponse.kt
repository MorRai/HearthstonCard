package com.rai.hearthstonecard.data.api

import com.google.gson.annotations.SerializedName

internal data class TokenResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,
)