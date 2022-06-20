package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.CardApi
import com.rai.hearthstonecard.data.api.TokenAuthenticator
import com.rai.hearthstonecard.data.api.TokenPrefs
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val networkModule = module {
    single {

        OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(TokenPrefs(get())))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${TokenPrefs(get()).fetchAuthToken()}").build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL_CARD)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create<CardApi>() }
}