package com.rai.hearthstonecard.data.database

import androidx.room.*
import com.rai.hearthstonecard.data.model.ClassPersonEntity

@Dao
internal interface ClassPersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(classPerson: ClassPersonEntity)

    @Update
    suspend fun update(classPerson: ClassPersonEntity)

    @Delete
    suspend fun delete(classPerson: ClassPersonEntity)

    @Query("SELECT * from hearthstone_classes WHERE id = :id")
    suspend fun getClass(id: Int): ClassPersonEntity

    @Query("SELECT * from hearthstone_classes order by name asc")
    suspend fun getClasses(): List<ClassPersonEntity>


}