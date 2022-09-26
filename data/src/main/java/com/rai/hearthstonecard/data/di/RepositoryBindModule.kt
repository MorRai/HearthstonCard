package com.rai.hearthstonecard.data.di

import com.rai.hearthstonecard.data.repository.CardLocalRepositoryImpl
import com.rai.hearthstonecard.data.repository.CardRemoteRepositoryImpl
import com.rai.hearthstonecard.data.repository.CityRemoteRepositoryImpl
import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository
import dagger.Binds
import dagger.Module


@Module
internal interface RepositoryBindModule {
    @Binds
    fun bindCardRemoteRepositoryImp_to_CardRemoteRepository(
        cardRemoteRepositoryImpl: CardRemoteRepositoryImpl
    ):CardRemoteRepository

    @Binds
    fun bindCityRemoteRepositoryImp_to_CityRemoteRepository(
        cityRemoteRepository: CityRemoteRepositoryImpl
    ):CityRemoteRepository

    @Binds
    fun bindCardLocalRepositoryImp_to_CardLocalRepository(
        cardLocalRepository: CardLocalRepositoryImpl
    ):CardLocalRepository
}

