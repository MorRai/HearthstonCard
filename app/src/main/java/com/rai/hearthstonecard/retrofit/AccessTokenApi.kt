package com.rai.hearthstonecard.retrofit

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AccessTokenApi {
    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
    ): TokenResponse

}