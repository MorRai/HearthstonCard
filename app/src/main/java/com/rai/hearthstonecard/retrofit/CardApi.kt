package com.rai.hearthstonecard.retrofit

import com.rai.hearthstonecard.model.ClassPerson
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
        @Query("pageSize") pageSize: Int,
        @Query("class") classSlug: String
    )
    : Cards


    @GET("hearthstone/metadata/classes?locale=en_US")
    suspend fun getClasses(
    ): List<ClassPerson>


}