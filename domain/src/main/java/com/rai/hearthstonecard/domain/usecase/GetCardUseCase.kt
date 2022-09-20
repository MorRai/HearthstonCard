package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCardUseCase(
    private val cardRemoteRepository: CardRemoteRepository,
    private val cardLocalRepository: CardLocalRepository
) {

    operator fun invoke(cardId :Int): Flow<LceState<Card>> = flow {
        val card = cardRemoteRepository.getCard(cardId)
            .fold(
                onSuccess = { card ->
                    LceState.Content(card)
                },
                onFailure = { err ->
                    LceState.Error(err)
                }
            )
        emit(card)

    }.onStart {
        emit(LceState.Content(cardLocalRepository.getCardFromDao(cardId)))
    }
}