package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.repository.RocketsRepository
import com.datienza.spacex.hiltbinder.ContributesBinding
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@ContributesBinding(SingletonComponent::class)
class GetRocketsUseCaseImpl @Inject constructor(
    private val repository: RocketsRepository,
) : GetRocketsUseCase {

    override suspend fun invoke(): List<Rocket> = repository.getRockets()
}
