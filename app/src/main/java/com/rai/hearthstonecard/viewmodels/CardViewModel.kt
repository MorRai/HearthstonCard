package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.*
import com.rai.hearthstonecard.repository.CardRepository
import com.rai.hearthstonecard.retrofit.Card

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {


    suspend fun getAllCards(page: Int): List<Card> {
        return cardRepository.getCards(page)
    }

    suspend fun retrieveCard(id: Int): Card {
        return cardRepository.getCard(id)
    }

}

class CardViewModelFactory(private val cardRepository: CardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}