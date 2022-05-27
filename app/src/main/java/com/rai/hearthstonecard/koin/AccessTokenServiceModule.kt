package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.retrofit.AccessTokenApi
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://us.battle.net/"
private const val CLIENT_ID = "12c065f57d084154838ac41b4a194ff5"
private const val CLIENT_SECRET = "sfu7x9nl3hjXVT1A4FJ7zkHtrqxmYc86"
private const val GRANT_TYPE = "client_credentials"

val accessTokenServiceModule = module {
    single(named("accessTokenRetrofit")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    single(named("accessToken")) {
        runBlocking {
            get<AccessTokenApi>(named("accessTokenService")).getToken(CLIENT_ID,
                CLIENT_SECRET,
                GRANT_TYPE)
        }.accessToken
    }

    single(named("accessTokenService")) { get<Retrofit>(named("accessTokenRetrofit")).create<AccessTokenApi>() }
}