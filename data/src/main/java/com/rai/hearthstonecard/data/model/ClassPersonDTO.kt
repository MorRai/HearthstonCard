package com.rai.hearthstonecard.data.model

internal data class ClassPersonDTO (
    val id: Int,
    val name: String,
    var image: String?,
    var slug: String?,
    val cardId: Int
)