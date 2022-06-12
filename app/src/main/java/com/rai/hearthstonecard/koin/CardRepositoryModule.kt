package com.rai.hearthstonecard.koin

import com.rai.hearthstonecard.repository.CardRepository
import org.koin.dsl.module

val cardRepositoryModule = module {
    single { CardRepository(get(),get(), get()) }
}