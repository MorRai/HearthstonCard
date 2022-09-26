package com.rai.hearthstonecard.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.data.services.LocationService
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCitiesUseCase
import com.rai.hearthstonecard.domain.usecase.GetClassesUseCase
import com.rai.hearthstonecard.ui.classes.PersonClassViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MapCityViewModel  @Inject constructor(
    getCitiesUseCase: GetCitiesUseCase, val locationService: LocationService
) : ViewModel() {
    val citiesFlow = getCitiesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )
}

class MapCityViewModelFactory @AssistedInject constructor(private val getCitiesUseCase: GetCitiesUseCase,
                                          private val locationService: LocationService) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == MapCityViewModel::class)
        return MapCityViewModel(getCitiesUseCase,locationService) as T
    }

    @AssistedFactory
    interface Factory {

        fun create(): MapCityViewModelFactory
    }
}