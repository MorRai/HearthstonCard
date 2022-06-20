package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.City
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCitiesUseCase(private val cityRemoteRepository: CityRemoteRepository) {

    operator fun invoke(): Flow<LceState<List<City>>> = flow {
        val cities = cityRemoteRepository.getCities()
            .fold(
                onSuccess = { cities ->
                    LceState.Content(cities)
                },
                onFailure = { err ->
                    LceState.Error(err)
                }
            )
        emit(cities)
    }
}