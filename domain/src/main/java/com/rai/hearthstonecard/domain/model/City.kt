package com.rai.hearthstonecard.domain.model

data class City(
    val id: String,
    val name: String?,
    val country: String?,
    val region: String?,
    val latitude: Double,
    val longitude: Double,
    val population: Int?
)
