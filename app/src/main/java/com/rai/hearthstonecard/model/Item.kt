package com.rai.hearthstonecard.model

import com.rai.hearthstonecard.domain.model.Card


sealed class Item {

    class Content(val data: Card): Item()

    object Loading : Item()
}