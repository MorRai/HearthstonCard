package com.rai.hearthstonecard.data.koin

import org.koin.dsl.module

val dataModule = module {
    includes(
        accessTokenServiceModule,
        cardDatabaseModule,
        cardRemoteRepositoryModule,
        cardLocalRepositoryModule,
        classesDatabaseModule,
        networkModule,
        useCaseModule
    )
}