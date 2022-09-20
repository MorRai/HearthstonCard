package com.rai.hearthstonecard.ui.cardDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class DetailCardViewModel  @Inject constructor(
    getCardUseCase: GetCardUseCase,
    cardId: Int,
) : ViewModel() {

    val cardFlow = getCardUseCase.invoke(cardId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )



}