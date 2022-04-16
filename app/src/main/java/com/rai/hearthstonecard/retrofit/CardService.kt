package com.rai.hearthstonecard.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object CardService {

     private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "7ab183b67emsh3f7f3870d1ae5c6p195056jsn30fae2a34bee")
            .build()
        chain.proceed(newRequest)
    }.build()

    private val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://omgvamp-hearthstone-v1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

     private val cardApi by lazy {retrofit.create<CardApi>() }


    fun providerCardApi() : CardApi {
        return cardApi
    }

}