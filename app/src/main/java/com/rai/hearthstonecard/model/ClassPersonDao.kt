package com.rai.hearthstonecard.model

import androidx.room.*

@Dao
interface ClassPersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(classPerson: ClassPerson)

    @Update
    suspend fun update(classPerson: ClassPerson)

    @Delete
    suspend fun delete(classPerson: ClassPerson)

    @Query("SELECT * from hearthstone_classes WHERE id = :id")
    suspend fun getClass(id: Int): ClassPerson

    @Query("SELECT * from hearthstone_classes")
    suspend fun getClasses(): List<ClassPerson>


}