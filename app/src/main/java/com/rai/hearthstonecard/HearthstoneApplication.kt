package com.rai.hearthstonecard

import android.app.Application
import com.rai.hearthstonecard.repository.CardRepository
import com.rai.hearthstonecard.model.CardDatabase
import com.rai.hearthstonecard.retrofit.CardService

class HearthstoneApplication : Application()  {
    lateinit var cardRepository: CardRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val cardService = CardService.providerCardApi()
        val database = CardDatabase.getDatabase(applicationContext)
        cardRepository = CardRepository( cardService, database, applicationContext)
    }
}