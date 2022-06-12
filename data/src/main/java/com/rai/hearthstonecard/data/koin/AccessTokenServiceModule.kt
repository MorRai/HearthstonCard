package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.AccessTokenApi
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


internal val accessTokenServiceModule = module {
    single(named("accessTokenRetrofit")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    single(named("accessToken")) {
         suspend {get<AccessTokenApi>(named("accessTokenService")).getToken(BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.GRANT_TYPE)
        .accessToken}
    }

    single(named("accessTokenService")) { get<Retrofit>(named("accessTokenRetrofit")).create<AccessTokenApi>() }
}