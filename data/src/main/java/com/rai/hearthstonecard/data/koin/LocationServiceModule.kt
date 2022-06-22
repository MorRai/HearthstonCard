package com.rai.hearthstonecard.data.koin

import org.koin.core.module.dsl.singleOf
import com.rai.hearthstonecard.data.services.LocationService
import org.koin.dsl.module

internal val locationServiceModule  = module {
    singleOf(::LocationService)
}