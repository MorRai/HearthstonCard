package com.rai.hearthstonecard.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardApi {

    @GET("hearthstone/cards/{id}?locale=en_US")
    suspend fun getCard(
        @Path("id") id: Int,
    ): Card


    @GET("hearthstone/cards?locale=en_US")
    suspend fun getCards(
        @Query("page") page: Int,
    )
    : Cards

}