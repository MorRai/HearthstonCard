package com.rai.hearthstonecard.model

import androidx.room.*
import com.rai.hearthstonecard.retrofit.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cards: List<Card>)

    @Update
    suspend fun update(card: Card)

    @Delete
    suspend fun delete(card: Card)

    @Query("SELECT * from hearthstone_card WHERE id = :id")
    suspend fun getCard(id: Int): Card

    @Query("SELECT * from hearthstone_card  WHERE class_id = :classId")
    suspend fun getCards(classId: Int): List<Card>

}