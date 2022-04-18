package com.rai.hearthstonecard.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class OAuthInterceptor(private val tokenType: String, private val acceesToken: String) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $acceesToken").build()
        return chain.proceed(request)
    }
}

object CardService {

    //var token:TokenResponse? = null

    private val client = OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", "USS69U46Mclue3VCyuOS7t8GCm3vQMvmgs"))
        //.addInterceptor(OAuthInterceptor(token!!.tokenType , token!!.accessToken))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://us.api.blizzard.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val cardApi by lazy { retrofit.create<CardApi>() }

    fun providerCardApi(): CardApi {
        return cardApi
    }

}