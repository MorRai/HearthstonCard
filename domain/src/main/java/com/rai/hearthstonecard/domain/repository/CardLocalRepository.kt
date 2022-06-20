package com.rai.hearthstonecard.domain.repository

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson

interface CardLocalRepository {
    suspend fun getCardFromDao(id: Int): Card

    suspend fun insertCards(cards: List<Card>)

    suspend fun getCardsFromDao(classId: Int):List<Card>

    suspend fun insertClasses(classPerson: ClassPerson)

    suspend fun getClassesFromDao():List<ClassPerson>

}