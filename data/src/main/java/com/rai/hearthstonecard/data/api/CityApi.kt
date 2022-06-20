package com.rai.hearthstonecard.data.api

import com.rai.hearthstonecard.data.model.CitiesDTO
import com.rai.hearthstonecard.data.model.CityDTO
import com.rai.hearthstonecard.data.model.CityRezDTO
import retrofit2.http.GET
import retrofit2.http.Path

internal interface CityApi {
    //апи такое себе (бесплатная версия) лимит 10 поэтому отобрала 10 крупнейшик по населению городов
    //в рб
    @GET("cities?limit=10&countryIds=by&sort=-population")
    suspend fun getCities(
    ): CitiesDTO

    @GET("cities/{id}")
    suspend fun getCity(
        @Path("id") id: String,
    ): CityRezDTO
}