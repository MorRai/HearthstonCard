package com.rai.hearthstonecard.data.di

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.AccessTokenApi
import com.rai.hearthstonecard.data.api.CardApi
import com.rai.hearthstonecard.data.api.TokenAuthenticator
import com.rai.hearthstonecard.data.api.TokenPrefs
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class AccessTokenServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideAccessTokenApi(retrofit: Retrofit): AccessTokenApi {
        return retrofit.create(AccessTokenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccessToken(accessTokenApi: AccessTokenApi): String {
        return runBlocking { accessTokenApi.getToken(BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            BuildConfig.GRANT_TYPE)
            .accessToken}
    }

}