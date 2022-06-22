package com.rai.hearthstonecard.koin

import org.koin.dsl.module
import com.rai.hearthstonecard.data.services.LocationService
import org.koin.core.module.dsl.singleOf


internal val serviceModule = module {
    singleOf(::LocationService)
}