package com.rai.hearthstonecard.data.repository

import com.rai.hearthstonecard.data.api.CardApi
import com.rai.hearthstonecard.data.mapper.toDomainModels
import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import javax.inject.Inject

internal class CardRemoteRepositoryImpl @Inject constructor(
    private val cardService: CardApi,
) : CardRemoteRepository {

    override suspend fun getCard(id: Int): Result<Card> {
        return runCatching {
            cardService.getCard(id)
        }.map { it.toDomainModels() }
    }

    override suspend fun getCards(
        page: Int,
        pageSize: Int,
        classSlug: String,
        mana: String,
        attack: String,
        health: String,
    ): Result<List<Card>> {
        return runCatching {
            cardService.getCards(page, pageSize, classSlug, mana, attack, health).cards
        }.map { it.toDomainModels() }
    }

    override suspend fun getClasses(): Result<List<ClassPerson>> {
        return runCatching {
            cardService.getClasses()
        }.map { it.toDomainModels() }
    }

}