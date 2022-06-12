package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.repository.CardLocalRepository
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetClassesUseCase(
    private val cardRemoteRepository: CardRemoteRepository,
    private val cardLocalRepository: CardLocalRepository,
) {

     operator fun invoke(): Flow<LceState<List<ClassPerson>>> = flow{

        val classes = cardRemoteRepository.getClasses()
            .fold(
                onSuccess = {
                    it.onEach { classPerson ->
                        cardRemoteRepository.getCard(classPerson.cardId)
                            .fold(
                                onSuccess = { card ->
                                    classPerson.image = card.image
                                    cardLocalRepository.insertClasses(classPerson)
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
        emit(LceState.Content(cardLocalRepository.getClassesFromDao()))
    }

}