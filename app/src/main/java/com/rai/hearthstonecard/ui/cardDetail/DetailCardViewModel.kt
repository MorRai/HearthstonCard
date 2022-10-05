package com.rai.hearthstonecard.ui.cardDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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

class DetailCardViewModelFactory  @AssistedInject constructor(private val getCardUseCase: GetCardUseCase,
                                                              @Assisted("cardId")private val cardId: Int) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailCardViewModel(getCardUseCase,cardId) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("cardId")cardId: Int):DetailCardViewModelFactory
    }
}