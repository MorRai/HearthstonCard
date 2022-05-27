package com.rai.hearthstonecard.koin

import androidx.room.Room
import com.rai.hearthstonecard.model.ClassPersonDatabase
import org.koin.dsl.module

val classPersonDatabaseModule = module {
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

