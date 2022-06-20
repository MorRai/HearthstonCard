package com.rai.hearthstonecard.koin


import com.rai.hearthstonecard.data.services.LocationService
import org.koin.dsl.module

internal val serviceModule = module {
    single { ::LocationService }
}