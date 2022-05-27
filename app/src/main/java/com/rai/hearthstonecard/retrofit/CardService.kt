package com.rai.hearthstonecard.retrofit

import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OAuthInterceptor(private val token: String) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }

}

object CardService {

    private const val CLIENT_ID = "12c065f57d084154838ac41b4a194ff5"
    private const val CLIENT_SECRET = "sfu7x9nl3hjXVT1A4FJ7zkHtrqxmYc86"
    private const val GRANT_TYPE = "client_credentials"

    private val accessToken = try {
        runBlocking {
            AccessTokenService.tokenApi().getToken(CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)//в любом случае полученые данные потом нужны
        }.accessToken
    } catch (e: Exception) {
        ""
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor(accessToken))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://us.api.blizzard.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun providerCardApi(): CardApi {
        return retrofit.create(CardApi::class.java)
    }


}