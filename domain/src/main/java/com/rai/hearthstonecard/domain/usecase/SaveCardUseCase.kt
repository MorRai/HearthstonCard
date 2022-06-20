package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.repository.CardLocalRepository

class SaveCardUseCase(private val cardLocalRepository: CardLocalRepository) {

    suspend operator fun invoke(listCards: List<Card>) {
        cardLocalRepository.insertCards(listCards)
    }

}