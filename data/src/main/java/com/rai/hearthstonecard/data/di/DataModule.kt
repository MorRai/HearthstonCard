package com.rai.hearthstonecard.data.di

import dagger.Module

@Module(includes = [ClassesDatabaseModule::class,
    AccessTokenServiceModule::class,
    CardDatabaseModule::class,
    NetworkCityModule::class,
    NetworkModule::class,
    UseCaseModule::class])
class DataModule