package com.rai.hearthstonecard.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rai.hearthstonecard.retrofit.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}