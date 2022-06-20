package com.rai.hearthstonecard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rai.hearthstonecard.data.model.CardEntity

@Database(entities = [CardEntity::class], version = 1, exportSchema = false)
internal abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}