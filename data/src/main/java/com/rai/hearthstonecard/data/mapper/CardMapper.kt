package com.rai.hearthstonecard.data.mapper

import com.rai.hearthstonecard.data.model.CardDTO
import com.rai.hearthstonecard.data.model.CardEntity
import com.rai.hearthstonecard.domain.model.Card

internal fun List<CardDTO>.toDomainModels(): List<Card> {
    return map { it.toDomainModels() }
}


internal fun CardDTO.toDomainModels(): Card {
    return Card(
        id = id,
        name = name,
        image = image,
        flavorText = flavorText,
        text = text,
        artistName = artistName,
        collectible = collectible,
        classId = classId,
        mana = mana,
        health = health,
        attack = attack
    )
}


internal fun Card.toDomainModels(): CardEntity {
    return CardEntity(
        id = id,
        name = name,
        image = image,
        flavorText = flavorText,
        text = text,
        artistName = artistName,
        collectible = collectible,
        classId = classId,
        mana = mana,
        health = health,
        attack = attack
    )
}

internal fun CardEntity.toDomainModels(): Card {
    return Card(
        id = id,
        name = name,
        image = image,
        flavorText = flavorText,
        text = text,
        artistName = artistName,
        collectible = collectible,
        classId = classId,
        mana = mana,
        health = health,
        attack = attack
    )
}