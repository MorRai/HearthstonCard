package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.Card
import com.rai.hearthstonecard.domain.model.ClassPerson
import com.rai.hearthstonecard.domain.model.Filters
import com.rai.hearthstonecard.domain.repository.CardRemoteRepository


class GetCardListUseCase(
    private val cardRemoteRepository: CardRemoteRepository,
) {
    private var pageSize = 50

    suspend operator fun invoke(filterQuery: Filters,classPerson:ClassPerson, currentPage: Int ): Result<List<Card>> {
        return cardRemoteRepository.getCards(currentPage,
            pageSize,
            classPerson.slug ?: "",
            filterQuery.mana,
            filterQuery.attack,
            filterQuery.health)
    }
}