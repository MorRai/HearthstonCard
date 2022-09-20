package com.rai.hearthstonecard.data.repository

import com.rai.hearthstonecard.data.database.CardDatabase
import com.rai.hearthstonecard.data.database.ClassPersonDatabase
import com.rai.hearthstonecard.data.mapper.toDomainModels
import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import javax.inject.Inject

internal class CardLocalRepositoryImpl @Inject constructor(
    private val cardDatabase: CardDatabase,
    private val classesDatabase: ClassPersonDatabase,
) : CardLocalRepository {
    override suspend fun getCardFromDao(id: Int): Card {
        return cardDatabase.cardDao().getCard(id).toDomainModels()
    }

    override suspend fun insertCards(cards: List<Card>) {
        cardDatabase.cardDao().insert(cards.map { it.toDomainModels() })
    }

    override suspend fun getCardsFromDao(classId: Int): List<Card> {
        return cardDatabase.cardDao().getCards(classId).map { it.toDomainModels() }
    }

    override suspend fun insertClasses(classPerson: ClassPerson) {
        classesDatabase.classPersonDao().insert(classPerson.toDomainModels())
    }

    override suspend fun getClassesFromDao(): List<ClassPerson> {
        return classesDatabase.classPersonDao().getClasses().map { it.toDomainModels() }
    }
}