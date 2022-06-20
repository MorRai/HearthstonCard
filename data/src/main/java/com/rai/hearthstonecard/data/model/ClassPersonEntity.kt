package com.rai.hearthstonecard.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hearthstone_classes")
internal data class ClassPersonEntity(
    @PrimaryKey @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    var image: String?,
    @ColumnInfo(name = "slug")
    var slug: String?,
    @ColumnInfo(name = "card_id")
    val cardId: Int
)