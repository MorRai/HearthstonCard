package com.rai.hearthstonecard.repository


import android.content.Context
import com.rai.hearthstonecard.util.NetworkUtils
import com.rai.hearthstonecard.model.CardDatabase
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.retrofit.CardApi


class CardRepository(
    private val cardService: CardApi,
    private val cardDatabase: CardDatabase,
    private val applicationContext: Context,
) {

    suspend fun getCard(id: Int) : Card {
        return if (NetworkUtils.isInternetAvailable(applicationContext)) {
            cardService.getCard(id)
        } else {
            cardDatabase.cardDao().getCard(id)
        }

    }
    suspend fun getCardFromDao(id: Int) = cardDatabase.cardDao().getCard(id)

    suspend fun getCards(page: Int) : List<Card> {

        return if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = cardService.getCards(page).cards
            cardDatabase.cardDao().insert(result)
            result
        } else {
             cardDatabase.cardDao().getAll()
        }
    }
    suspend fun getCardsFromDao() = cardDatabase.cardDao().getAll()
}