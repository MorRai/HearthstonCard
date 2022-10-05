package com.rai.hearthstonecard.ui.cardList

import androidx.lifecycle.*
import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.model.Filters
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.usecase.GetCardListUseCase
import com.rai.hearthstonecard.domain.usecase.GetCardUseCase
import com.rai.hearthstonecard.domain.usecase.GetCardsDaoUseCase
import com.rai.hearthstonecard.domain.usecase.SaveCardUseCase
import com.rai.hearthstonecard.ui.cardDetail.DetailCardViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ListCardViewModel  @Inject constructor(
    private val getCardListUseCase: GetCardListUseCase,
    private val getCardsDaoUseCase: GetCardsDaoUseCase,
    private val saveCardUseCase: SaveCardUseCase,
    private val classPerson: ClassPerson,
) : ViewModel() {
    //надо как то нормально с лсе сделать и юзкейсами
    private val filter = Filters("", "", "")
    private val filterQuery = MutableStateFlow(filter)


    private var currentPage = 1
    private var isLoading = false

    private val loadCardsFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val mutableState = MutableStateFlow<LceState<List<Card>>>(LceState.Loading)
    val state: StateFlow<LceState<List<Card>>>
        get() = mutableState

    val cardsFlow = filterQuery
        .onEach {
            currentPage = 1
            isLoading = false
        }
        .flatMapLatest { query ->
            cardDataFlow(query)
        }
        .onStart {
            onLoadCards()
            emit(getCardsDaoUseCase.invoke(classPerson.id))
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadCards() {
        loadCardsFlow.tryEmit(Unit)
    }

    fun onFilterChanged(filter: Filters) {
        filterQuery.value = filter
    }

    fun getFilter():Filters {
        return filterQuery.value
    }

    private fun cardDataFlow(filterQuery: Filters): Flow<List<Card>> {
        return loadCardsFlow
            .filter { !isLoading }
            .onEach {
                isLoading = true
                mutableState.value = LceState.Loading
            }
            .map {
                getCardListUseCase.invoke(filterQuery,classPerson,currentPage)
                    .apply { isLoading = false }
                    .fold(
                        onSuccess = {
                            saveCardUseCase.invoke(it)
                            currentPage++
                            mutableState.value = LceState.Content(it)
                            it

                        },
                        onFailure = {
                            mutableState.value = LceState.Error(it)
                            emptyList()

                        }
                    )
            }
            .runningReduce { accumulator, value -> accumulator + value }
    }

}

class ListCardViewModelFactory @AssistedInject constructor(private val getCardListUseCase: GetCardListUseCase,
                                          private val getCardsDaoUseCase: GetCardsDaoUseCase,
                                          private val saveCardUseCase: SaveCardUseCase,
                                          @Assisted("classPerson")private val classPerson: ClassPerson,) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListCardViewModel(getCardListUseCase,getCardsDaoUseCase,saveCardUseCase,classPerson) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("classPerson")classPerson: ClassPerson):ListCardViewModelFactory
    }
}
