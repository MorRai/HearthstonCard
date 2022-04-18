package com.rai.hearthstonecard.retrofit

data class Cards(
    val cards: List<CardItem.Card>,
)

sealed class CardItem {

    data class Card(
        val id: Int,
        val name: String,
        val image: String,
        val flavorText: String,
        val text: String,
        val artistName: String,
        val collectible: Int,
    ) : CardItem()

    object Loading : CardItem()


}

