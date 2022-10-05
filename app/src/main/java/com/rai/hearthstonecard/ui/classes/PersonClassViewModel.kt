package com.rai.hearthstonecard.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCityUseCase
import com.rai.hearthstonecard.domain.usecase.GetClassesUseCase
import com.rai.hearthstonecard.ui.city.MapInfoViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PersonClassViewModel @Inject constructor(getClassesUseCase: GetClassesUseCase) : ViewModel() {

    val classesFlow = getClassesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )
}

class PersonClassViewModelFactory @Inject constructor(private val getClassesUseCase: GetClassesUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonClassViewModel(getClassesUseCase) as T
    }

}