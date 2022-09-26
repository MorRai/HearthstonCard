package com.rai.hearthstonecard

import android.app.Application
import android.content.Context
import com.rai.hearthstonecard.di.AppComponent
import com.rai.hearthstonecard.di.DaggerAppComponent

class HearthstoneApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(context = this).build()

    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is HearthstoneApplication -> appComponent
        else -> applicationContext.appComponent
    }