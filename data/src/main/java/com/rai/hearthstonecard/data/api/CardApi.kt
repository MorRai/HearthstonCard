package com.rai.hearthstonecard.data.api

import com.rai.hearthstonecard.data.model.CardDTO
import com.rai.hearthstonecard.data.model.CardsDTO
import com.rai.hearthstonecard.data.model.ClassPersonDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CardApi {

    @GET("hearthstone/cards/{id}?locale=en_US")
    suspend fun getCard(
        @Path("id") id: Int,
    ): CardDTO


    @GET("hearthstone/cards?locale=en_US")
    suspend fun getCards(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("class") classSlug: String
    )
    : CardsDTO


    @GET("hearthstone/metadata/classes?locale=en_US")
    suspend fun getClasses(
    ): List<ClassPersonDTO>


}