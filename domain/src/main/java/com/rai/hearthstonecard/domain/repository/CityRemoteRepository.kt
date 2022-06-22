package com.rai.hearthstonecard.domain.repository

import com.rai.hearthstonecard.domain.model.City

interface CityRemoteRepository {

    suspend fun getCities(): Result<List<City>>

    suspend fun getCity(id:String): Result<City>
}