package com.rai.hearthstonecard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.hearthstonecard.repository.CardRepository
import com.rai.hearthstonecard.util.LceState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class PersonClassViewModel(private val cardRepository: CardRepository) : ViewModel() {

    val classesFlow = flow {
        val classes = cardRepository.getClasses()
            .fold(
                onSuccess = {
                    it.onEach { classPerson ->
                        cardRepository.getCard(classPerson.cardId)
                            .fold(
                                onSuccess = { card ->
                                    classPerson.image = card.image
                                    cardRepository.insertClasses(classPerson)
                                },
                                onFailure = {
                                    // покуда как загулушка ибо у одной из них всегда пусто потом надо норм что сделать как время будет
                                    classPerson.image =
                                        "https://preview.redd.it/8ed90rync7u71.png?width=400&format=png&auto=webp&s=219606bfd4b139813025cc259aa096a143d61d8f"
                                }
                            )
                    }
                    LceState.Content(it)
                },
                onFailure = {
                    LceState.Error(it)
                }
            )
        emit(classes)
    }.onStart {
        emit(LceState.Content(cardRepository.getClassesFromDao()))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LceState.Loading
        )
}