package com.rai.hearthstonecard.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CardApi {

    @GET("cardbacks")
    fun getCards() : Call<List<Card>>

}