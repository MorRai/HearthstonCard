package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.retrofit.CardApi
import com.rai.hearthstonecard.retrofit.TokenAuthenticator
import com.rai.hearthstonecard.retrofit.TokenPrefs
import okhttp3.OkHttpClient
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create



val cardServiceModule = module {

    single {

        OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(get()))
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${TokenPrefs(get()).fetchAuthToken()}").build()
                chain.proceed(request)
            }

            //.addInterceptor(OAuthInterceptor(get(named("accessToken"))))
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://us.api.blizzard.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create<CardApi>() }
}