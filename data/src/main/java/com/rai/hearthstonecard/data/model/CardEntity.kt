package com.rai.hearthstonecard.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hearthstone_card")
internal data class CardEntity(
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
    val classId: Int?,
    @ColumnInfo(name = "mana")
    val mana: String?,
    @ColumnInfo(name = "attack")
    val attack: String?,
    @ColumnInfo(name = "health")
    val health: String?
)