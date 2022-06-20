package com.rai.hearthstonecard.domain.usecase

import com.rai.hearthstonecard.domain.model.City
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.domain.repository.CityRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCityUseCase(private val cityRemoteRepository: CityRemoteRepository) {

    operator fun invoke(id :String): Flow<LceState<City>> = flow {
        val city = cityRemoteRepository.getCity(id)
            .fold(
                onSuccess = { city ->
                    LceState.Content(city)
                },
                onFailure = { err ->
                    LceState.Error(err)
                }
            )
        emit(city)
    }
}