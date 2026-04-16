package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.repository.RocketsRepository
import com.datienza.spacex.hiltbinder.ContributesBinding
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ContributesBinding(SingletonComponent::class)
class GetRocketsUseCaseImpl @Inject constructor(
    private val repository: RocketsRepository,
) : GetRocketsUseCase {

    override fun invoke(): Single<List<Rocket>> = repository.getRockets()
}
