package com.rai.hearthstonecard.data.di

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.CardApi
import com.rai.hearthstonecard.data.api.TokenAuthenticator
import com.rai.hearthstonecard.data.api.TokenPrefs
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Singleton
    @Provides
    @CardOkHttpClient
    fun provideOkhttpClient(tokenAuthenticator:TokenAuthenticator,tokenPrefs:TokenPrefs ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${tokenPrefs.fetchAuthToken()}").build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    @CardRetrofit
    fun provideRetrofit( @CardOkHttpClient okHttpClient:OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL_CARD)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCardApi( @CardRetrofit retrofit: Retrofit): CardApi {
        return retrofit.create(CardApi::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class CardRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class CardOkHttpClient
}