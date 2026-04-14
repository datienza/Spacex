package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.repository.RocketsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRocketsUseCaseImpl @Inject constructor(
    private val repository: RocketsRepository,
) : GetRocketsUseCase {

    override fun invoke(): Single<List<Rocket>> = repository.getRockets()
}
