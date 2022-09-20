package com.rai.hearthstonecard.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCityUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MapInfoViewModel  @Inject constructor(
    getCityUseCase: GetCityUseCase,
    id: String
) : ViewModel() {

    val cityFlow = getCityUseCase.invoke(id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )
}