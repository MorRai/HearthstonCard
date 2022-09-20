package com.rai.hearthstonecard.data.di

import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository
import com.rai.hearthstonecard.domain.usecase.*
import dagger.Module
import dagger.Provides


@Module
internal class UseCaseModule {
    @Provides
    fun provideGetCardListUseCase(cardRemoteRepository: CardRemoteRepository): GetCardListUseCase {
        return GetCardListUseCase(cardRemoteRepository)
    }
    @Provides
    fun provideGetCardsDaoUseCase(cardLocalRepository: CardLocalRepository): GetCardsDaoUseCase {
        return GetCardsDaoUseCase(cardLocalRepository)
    }
    @Provides
    fun provideGetCardUseCase(cardRemoteRepository: CardRemoteRepository,
                              cardLocalRepository: CardLocalRepository): GetCardUseCase {
        return GetCardUseCase(cardRemoteRepository,cardLocalRepository)
    }
    @Provides
    fun provideGetCitiesUseCase(cityRemoteRepository: CityRemoteRepository): GetCitiesUseCase {
        return GetCitiesUseCase(cityRemoteRepository)
    }
    @Provides
    fun provideGetCityUseCase(cityRemoteRepository: CityRemoteRepository): GetCityUseCase {
        return GetCityUseCase(cityRemoteRepository)
    }
    @Provides
    fun provideGetClassesUseCase(cardRemoteRepository: CardRemoteRepository,
                                 cardLocalRepository: CardLocalRepository,): GetClassesUseCase {
        return GetClassesUseCase(cardRemoteRepository,cardLocalRepository)
    }
    @Provides
    fun provideSaveCardUseCase(cardLocalRepository: CardLocalRepository): SaveCardUseCase {
        return SaveCardUseCase(cardLocalRepository)
    }


}