package com.rai.hearthstonecard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rai.hearthstonecard.data.model.ClassPersonEntity

@Database(entities = [ClassPersonEntity::class], version = 1, exportSchema = false)
internal abstract class ClassPersonDatabase: RoomDatabase() {
    abstract fun classPersonDao(): ClassPersonDao
}