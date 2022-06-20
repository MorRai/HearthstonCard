package com.rai.hearthstonecard.domain.services



interface LocationService {

    fun getLocationFlow()

    suspend fun getCurrentLocation()

}