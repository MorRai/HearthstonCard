package com.rai.hearthstonecard

import android.app.Application
import com.rai.hearthstonecard.data.koin.dataModule
import com.rai.hearthstonecard.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HearthstoneApplication : Application()  {

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
}