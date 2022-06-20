package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.koin.serviceModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        accessTokenServiceModule,
        cardDatabaseModule,
        cardRemoteRepositoryModule,
        cardLocalRepositoryModule,
        cityRemoteRepositoryModule,
        classesDatabaseModule,
        networkModule,
        useCaseModule,
        serviceModule,
        locationServiceModule,
        cityModule
    )
}