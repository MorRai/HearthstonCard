package com.rai.hearthstonecard.retrofit

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Cards(
    val cards: List<Card>,
)

@Entity(tableName = "hearthstone_card")
data class Card(
    @PrimaryKey @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "flavor_text")
    val flavorText: String?,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "artist_name")
    val artistName: String?,
    @ColumnInfo(name = "collectible")
    val collectible: Int?,
    @ColumnInfo(name = "class_id")
    val classId: Int?
    )


