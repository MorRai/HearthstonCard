package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.data.repository.CardRemoteRepositoryImpl
import com.rai.hearthstonecard.data.repository.CardLocalRepositoryImpl
import com.rai.hearthstonecard.data.repository.CityRemoteRepositoryImpl
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository
import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val cardRemoteRepositoryModule = module {
    singleOf(::CardRemoteRepositoryImpl){bind<CardRemoteRepository>() }
}

internal val cityRemoteRepositoryModule = module {
    singleOf(::CityRemoteRepositoryImpl){bind<CityRemoteRepository>() }
}

internal val cardLocalRepositoryModule = module {
    singleOf(::CardLocalRepositoryImpl){bind<CardLocalRepository>() }
}

