package com.rai.hearthstonecard.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object AccessTokenService {

    private const val BASE_URL =
        "https://us.battle.net/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val accessTokenApi by lazy { retrofit.create<AccessTokenApi>() }

    fun tokenApi(): AccessTokenApi {
        return accessTokenApi
    }

}