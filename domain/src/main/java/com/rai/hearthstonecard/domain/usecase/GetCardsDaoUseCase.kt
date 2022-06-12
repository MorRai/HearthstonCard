package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.repository.CardLocalRepository

class GetCardsDaoUseCase(private val cardLocalRepository: CardLocalRepository) {
    suspend operator fun invoke(classPersonId: Int): List<Card> {
        return cardLocalRepository.getCardsFromDao(classPersonId)
    }
}