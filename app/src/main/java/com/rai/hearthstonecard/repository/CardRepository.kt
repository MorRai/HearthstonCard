package com.rai.hearthstonecard.repository

import com.rai.hearthstonecard.model.CardDatabase
import com.rai.hearthstonecard.model.ClassPerson
import com.rai.hearthstonecard.model.ClassPersonDatabase
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.retrofit.CardApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CardRepository(
    private val cardService: CardApi,
    private val cardDatabase: CardDatabase,
    private val classesDatabase: ClassPersonDatabase,
) {

    suspend fun getCard(id: Int) = withContext(Dispatchers.IO) {
        runCatching {
            cardService.getCard(id)
        }
    }

    suspend fun getCardFromDao(id: Int) = cardDatabase.cardDao().getCard(id)


    suspend fun insertCards(cards: List<Card>) {
        cardDatabase.cardDao().insert(cards)
    }

    suspend fun getCards(page: Int, pageSize: Int, classSlug: String) =
        withContext(Dispatchers.IO) {
            runCatching {
                cardService.getCards(page, pageSize, classSlug).cards
            }
        }

    suspend fun getCardsFromDao(classId: Int) = cardDatabase.cardDao().getCards(classId)

    suspend fun getClasses() = withContext(Dispatchers.IO) {
        runCatching { cardService.getClasses() }
    }

    suspend fun insertClasses(classPerson: ClassPerson) {
        classesDatabase.classPersonDao().insert(classPerson)
    }

    suspend fun getClassesFromDao() = classesDatabase.classPersonDao().getClasses()

}