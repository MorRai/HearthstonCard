package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.repository.CardRepository
import com.rai.hearthstonecard.util.LceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class DetailCardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    val cardIdStateFlow =
        MutableStateFlow(0)//лучше так или передавать в конструктор как с ListCardViewModel ?
    val cardFlow = cardIdStateFlow.mapLatest {
        cardRepository.getCard(it)
            .fold(
                onSuccess = { card ->
                    LceState.Content(card)
                },
                onFailure = { err ->
                    LceState.Error(err)
                }
            )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )

}