package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.repository.RocketsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRocketsUseCaseImpl @Inject constructor(
    private val repository: RocketsRepository
): GetRocketsUseCase {

    override fun invoke(): Single<List<Rocket>> {
        return repository.getRockets()
    }
}