package com.rai.hearthstonecard.data.repository

import com.rai.hearthstonecard.data.api.CityApi
import com.rai.hearthstonecard.data.mapper.toDomainModels
import com.rai.hearthstonecard.domain.model.City
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository

internal class CityRemoteRepositoryImpl(private val cityService: CityApi) :
    CityRemoteRepository {
    override suspend fun getCities(): Result<List<City>> {

        return runCatching { cityService.getCities().data.map { it.toDomainModels() } }
    }

    override suspend fun getCity(id:String): Result<City> {
        return runCatching { cityService.getCity(id).data.toDomainModels()}
    }
}