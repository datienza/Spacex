package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.repository.LaunchesRepository
import com.datienza.spacex.domain.repository.RocketsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLaunchesUseCaseImpl @Inject constructor(
    private val repository: LaunchesRepository
): GetLaunchesUseCase {

    override fun invoke(rocketId: String): Single<List<Launch>> {
        return repository.getLaunches(rocketId)
    }
}