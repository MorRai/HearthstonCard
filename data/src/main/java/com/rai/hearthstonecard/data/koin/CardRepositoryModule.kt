package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.data.repository.CardRepositoryImpl
import com.rai.hearthstonecard.domain.repository.CardRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val cardRepositoryModule = module {
    singleOf(::CardRepositoryImpl){bind<CardRepository>()}
}