package com.rai.hearthstonecard.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class OAuthInterceptor(private val token: String) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}

object CardService {

    fun providerCardApi(token: String): CardApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor(token))
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://us.api.blizzard.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cardApi by lazy { retrofit.create<CardApi>() }

        return cardApi
    }

}