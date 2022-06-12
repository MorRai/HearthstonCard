package com.rai.hearthstonecard.data.database

import androidx.room.*
import com.rai.hearthstonecard.data.model.CardEntity

@Dao
internal interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cards: List<CardEntity>)

    @Update
    suspend fun update(card: CardEntity)

    @Delete
    suspend fun delete(card: CardEntity)

    @Query("SELECT * from hearthstone_card WHERE id = :id")
    suspend fun getCard(id: Int): CardEntity

    @Query("SELECT * from hearthstone_card  WHERE class_id = :classId order by name asc")
    suspend fun getCards(classId: Int): List<CardEntity>

}