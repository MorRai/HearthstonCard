package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    factoryOf(::GetClassesUseCase)
    factoryOf(::GetCardUseCase)
    factoryOf(::GetCardListUseCase)
    factoryOf(::GetCardsDaoUseCase)
    factoryOf(::SaveCardUseCase)
    factoryOf(::GetCitiesUseCase)
    factoryOf(::GetCityUseCase)
}