package com.rai.hearthstonecard.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardApi {

    @GET("hearthstone/cards/{id}?locale=en_US")
    fun getCard(
        @Path("id") id: Int,
    ): Call<CardItem.Card>


    @GET("hearthstone/cards?locale=en_US")
    fun getCards(
        @Query("page") page: Int,
    ): Call<Cards>

}