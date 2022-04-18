package com.rai.hearthstonecard.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AccessTokenApi {
    @POST("oauth/token")
    @FormUrlEncoded
    fun getToken(
        @Field("client_id") client_id: String?,
        @Field("client_secret") client_secret: String?,
        @Field("grant_type") grant_type: String?,
    ): Call<TokenResponse>
}