package com.rai.hearthstonecard.data.koin

import com.rai.hearthstonecard.domain.usecase.GetClassesUseCase
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import com.rai.hearthstonecard.domain.usecase.GetCardListUseCase
import com.rai.hearthstonecard.domain.usecase.GetCardsDaoUseCase
import com.rai.hearthstonecard.domain.usecase.SaveCardUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetClassesUseCase)
    factoryOf(::GetCardUseCase)
    factoryOf(::GetCardListUseCase)
    factoryOf(::GetCardsDaoUseCase)
    factoryOf(::SaveCardUseCase)
}