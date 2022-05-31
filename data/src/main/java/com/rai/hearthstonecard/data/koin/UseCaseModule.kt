package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.domain.usecase.GetClassesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetClassesUseCase)
}