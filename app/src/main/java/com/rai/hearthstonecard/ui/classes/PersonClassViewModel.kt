package com.rai.hearthstonecard.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetClassesUseCase
import kotlinx.coroutines.flow.*

class PersonClassViewModel(getClassesUseCase: GetClassesUseCase) : ViewModel() {

    val classesFlow = getClassesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )
}