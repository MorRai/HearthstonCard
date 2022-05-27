package com.rai.hearthstonecard.koin

import androidx.room.Room
import com.rai.hearthstonecard.model.CardDatabase
import org.koin.dsl.module


val cardDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CardDatabase::class.java,
            "hearthstone_card_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}