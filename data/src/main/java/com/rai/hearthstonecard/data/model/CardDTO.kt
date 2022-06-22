package com.rai.hearthstonecard.data.model

internal data class CardsDTO(
    val cards: List<CardDTO>,
)

internal data class CardDTO(
    val id: Int,
    val name: String,
    val image: String?,
    val flavorText: String?,
    val text: String?,
    val artistName: String?,
    val collectible: Int?,
    val classId: Int?,
    val mana: String?,
    val attack: String?,
    val health: String?,
)