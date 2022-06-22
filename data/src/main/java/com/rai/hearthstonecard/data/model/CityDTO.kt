package com.rai.hearthstonecard.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


internal data class CityRezDTO(
    @SerializedName("data")
    @Expose
    val data: CityDTO
)

internal data class CitiesDTO(
    @SerializedName("data")
    @Expose
    val data: List<CityDTO>,
)


internal data class CityDTO(
    val id: String,
    val name: String?,
    val country: String?,
    val region: String?,
    val latitude: Double,
    val longitude: Double,
    val population: Int?
)