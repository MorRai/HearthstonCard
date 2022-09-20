package com.rai.hearthstonecard.di

import com.rai.hearthstonecard.HearthstoneApplication
import com.rai.hearthstonecard.data.di.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppModule::class,
    DataModule::class])
interface AppComponent : AndroidInjector<HearthstoneApplication>{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: HearthstoneApplication): AppComponent
    }

}