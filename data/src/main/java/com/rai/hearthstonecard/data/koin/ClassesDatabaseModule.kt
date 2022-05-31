package com.rai.hearthstonecard.data.koin

import androidx.room.Room
import com.rai.hearthstonecard.data.database.ClassPersonDatabase
import org.koin.dsl.module

internal val classesDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ClassPersonDatabase::class.java,
            "hearthstone_classes_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}