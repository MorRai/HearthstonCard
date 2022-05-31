package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.repository.CardRepository

class GetClassesUseCase(private val cardRepository: CardRepository) {

    suspend operator fun invoke():Result<List<ClassPerson>>{
        return  cardRepository.getClasses()
    }
}