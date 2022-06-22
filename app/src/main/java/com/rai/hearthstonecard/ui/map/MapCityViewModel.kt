package com.rai.hearthstonecard.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.data.services.LocationService
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MapCityViewModel(
    getCitiesUseCase: GetCitiesUseCase, val locationService: LocationService,
) : ViewModel() {
    val citiesFlow = getCitiesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )

}