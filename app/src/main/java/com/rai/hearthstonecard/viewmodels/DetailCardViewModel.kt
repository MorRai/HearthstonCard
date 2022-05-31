package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.repository.CardRepository
import com.rai.hearthstonecard.util.LceState
import kotlinx.coroutines.flow.*

class DetailCardViewModel(
    private val cardRepository: CardRepository,
    private val cardId: Int,
) : ViewModel() {

    val cardFlow = flow {
        val card = cardRepository.getCard(cardId)
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
        emit(LceState.Content(cardRepository.getCardFromDao(cardId)))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )

}