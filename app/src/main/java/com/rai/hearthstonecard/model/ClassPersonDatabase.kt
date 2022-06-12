package com.rai.hearthstonecard.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClassPerson::class], version = 1, exportSchema = false)
abstract class ClassPersonDatabase: RoomDatabase() {
    abstract fun classPersonDao(): ClassPersonDao
}