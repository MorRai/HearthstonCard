package com.rai.hearthstonecard.data.repository

import com.rai.hearthstonecard.data.api.CardApi
import com.rai.hearthstonecard.data.database.CardDatabase
import com.rai.hearthstonecard.data.database.ClassPersonDatabase
import com.rai.hearthstonecard.data.mapper.toDomainModels
import com.rai.hearthstonecard.data.model.ClassPersonDTO
import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.repository.CardRepository

internal class CardRepositoryImpl(private val cardService: CardApi,
                         private val cardDatabase: CardDatabase,
                         private val classesDatabase: ClassPersonDatabase
): CardRepository {

    override suspend fun getCard(id: Int): Result<Card> {
        return runCatching {
            cardService.getCard(id)
        }.map { it.toDomainModels() }
    }

    override suspend fun getCardFromDao(id: Int): Card {
        return cardDatabase.cardDao().getCard(id).toDomainModels()
    }

    override suspend fun insertCards(cards: List<Card>) {
        cardDatabase.cardDao().insert(cards.map { it.toDomainModels() })
    }

    override suspend fun getCards(page: Int, pageSize: Int, classSlug: String): Result<List<Card>> {
        return runCatching {
            cardService.getCards(page, pageSize, classSlug).cards
        }.map { it.toDomainModels() }
    }

    override suspend fun getCardsFromDao(classId: Int): List<Card> {
        return cardDatabase.cardDao().getCards(classId).map { it.toDomainModels() }
    }

    override suspend fun getClasses(): Result<List<ClassPerson>> {
        return runCatching {
            cardService.getClasses()
        }.map { it.toDomainModels() }
    }

    override suspend fun insertClasses(classPerson: ClassPerson) {
        classesDatabase.classPersonDao().insert(classPerson.toDomainModels())
    }

    override suspend fun getClassesFromDao(): List<ClassPerson> {
        return classesDatabase.classPersonDao().getClasses().map { it.toDomainModels() }
    }

}