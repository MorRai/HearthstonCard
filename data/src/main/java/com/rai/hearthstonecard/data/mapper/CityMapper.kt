package com.rai.hearthstonecard.data.mapper

import com.rai.hearthstonecard.data.model.CityDTO
import com.rai.hearthstonecard.domain.model.City

internal fun CityDTO.toDomainModels(): City {
    return City(
        id = id,
        name = name,
        country = country,
        region = region,
        latitude = latitude,
        longitude = longitude,
        population = population
    )
}
