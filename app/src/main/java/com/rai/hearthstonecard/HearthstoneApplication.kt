package com.rai.hearthstonecard

import com.rai.hearthstonecard.data.koin.dataModule
import com.rai.hearthstonecard.di.DaggerAppComponent
import com.rai.hearthstonecard.koin.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HearthstoneApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HearthstoneApplication)
            modules(
                dataModule,
                viewModelsModule

            )
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory()
            .create(this)
    }
}