package com.rai.hearthstonecard.data.di

import android.content.Context
import androidx.room.Room
import com.rai.hearthstonecard.data.database.CardDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class CardDatabaseModule {

    @Singleton
    @Provides
    fun provideCardDatabaseModule(context: Context): CardDatabase {
        return  Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            "hearthstone_card_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}