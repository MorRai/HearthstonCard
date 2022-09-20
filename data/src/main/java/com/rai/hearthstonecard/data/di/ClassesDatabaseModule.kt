package com.rai.hearthstonecard.data.di

import android.content.Context
import androidx.room.Room
import com.rai.hearthstonecard.data.database.ClassPersonDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal class ClassesDatabaseModule {

    @Singleton
    @Provides
    fun provideClassPersonDatabase(context: Context): ClassPersonDatabase{
        return  Room.databaseBuilder(
            context,
            ClassPersonDatabase::class.java,
            "hearthstone_classes_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}