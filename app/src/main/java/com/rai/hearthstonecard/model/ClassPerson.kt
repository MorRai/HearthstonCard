package com.rai.hearthstonecard.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hearthstone_classes")
data class ClassPerson(
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
): Parcelable