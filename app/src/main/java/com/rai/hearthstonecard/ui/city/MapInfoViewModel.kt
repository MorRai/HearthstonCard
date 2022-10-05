package com.rai.hearthstonecard.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import com.rai.hearthstonecard.domain.usecase.GetCityUseCase
import com.rai.hearthstonecard.ui.cardDetail.DetailCardViewModel
import com.rai.hearthstonecard.ui.cardList.ListCardViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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

class MapInfoViewModelFactory @AssistedInject constructor(private val getCityUseCase: GetCityUseCase,
                                          @Assisted("id")private val id: String) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapInfoViewModel(getCityUseCase,id) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("id")id: String): MapInfoViewModelFactory
    }
}