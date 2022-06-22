package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.BuildConfig
import com.rai.hearthstonecard.data.api.CityApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val cityModule = module {

    single(named("clientCity")) {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", "7ab183b67emsh3f7f3870d1ae5c6p195056jsn30fae2a34bee")
                    .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single(named("retrofitCity")) {
        Retrofit.Builder()
            .client(get(named("clientCity")))
            .baseUrl(BuildConfig.BASE_URL_CITY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named("retrofitCity")).create<CityApi>() }
}