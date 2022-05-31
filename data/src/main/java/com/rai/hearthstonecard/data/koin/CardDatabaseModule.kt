package com.rai.hearthstonecard.data.koin

import androidx.room.Room
import com.rai.hearthstonecard.data.database.CardDatabase
import org.koin.dsl.module

internal val cardDatabaseModule = module {
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