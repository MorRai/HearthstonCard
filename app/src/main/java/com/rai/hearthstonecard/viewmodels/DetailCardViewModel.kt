package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import kotlinx.coroutines.flow.*

class DetailCardViewModel(
    getCardUseCase: GetCardUseCase,
    cardId: Int,
) : ViewModel() {

    val cardFlow = getCardUseCase.invoke(cardId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )

}