package com.datienza.spacex.data.launches.usecase

import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.repository.LaunchesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLaunchesUseCaseImpl @Inject constructor(
    private val repository: LaunchesRepository,
) : GetLaunchesUseCase {

    override fun invoke(rocketId: String): Single<List<Launch>> =
        repository.getLaunches(rocketId)
}
