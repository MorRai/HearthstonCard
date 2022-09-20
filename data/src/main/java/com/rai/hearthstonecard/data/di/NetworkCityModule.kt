package com.rai.hearthstonecard.data.di

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.CityApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class NetworkCityModule {
    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", "7ab183b67emsh3f7f3870d1ae5c6p195056jsn30fae2a34bee")
                    .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL_CITY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCityApi(retrofit: Retrofit): CityApi {
        return retrofit.create(CityApi::class.java)
    }


}