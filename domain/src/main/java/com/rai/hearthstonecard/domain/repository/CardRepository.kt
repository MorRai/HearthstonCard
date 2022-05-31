package com.rai.hearthstonecard.domain.repository

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson

interface CardRepository {

    suspend fun getCard(id: Int): Result<Card>

    suspend fun getCardFromDao(id: Int): Card

    suspend fun insertCards(cards: List<Card>)

    suspend fun getCards(page: Int, pageSize: Int, classSlug: String):Result<List<Card>>

    suspend fun getCardsFromDao(classId: Int):List<Card>

    suspend fun getClasses():Result<List<ClassPerson>>

    suspend fun insertClasses(classPerson: ClassPerson)

    suspend fun getClassesFromDao():List<ClassPerson>

}