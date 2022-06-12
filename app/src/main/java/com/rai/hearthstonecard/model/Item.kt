package com.rai.hearthstonecard.model

import com.rai.hearthstonecard.retrofit.Card

sealed class Item {

    class Content(val data: Card): Item()

    object Loading : Item()
}