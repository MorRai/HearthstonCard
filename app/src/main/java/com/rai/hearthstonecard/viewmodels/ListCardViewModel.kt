package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.*
import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.repository.CardRepository
import com.rai.hearthstonecard.util.LceState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class ListCardViewModel(
    private val cardRepository: CardRepository,
    private val classPerson: ClassPerson,
) : ViewModel() {
    private var currentPage = 1
    private var pageSize = 50
    private var isLoading = false

    private val loadCardsFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var listCard: List<Card> = listOf()

    val cardsFlow = loadCardsFlow
        .filter { !isLoading }
        .onEach {
            isLoading = true
        }
        .map {
            cardRepository.getCards(currentPage, pageSize, classPerson.slug ?: "")
                .apply { isLoading = false }
                .fold(
                    onSuccess = {
                        cardRepository.insertCards(it)
                        currentPage++
                        listCard = listCard + it
                        LceState.Content(listCard)
                    },
                    onFailure = {
                        LceState.Error(it)
                    }
                )
        }
        .onStart {
            emit(LceState.Content(cardRepository.getCardsFromDao(classPerson.id)))
            onLoadCards()
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadCards() {
        loadCardsFlow.tryEmit(Unit)
    }

}
