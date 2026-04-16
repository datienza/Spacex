package com.datienza.spacex.data.launches.usecase

import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.repository.LaunchesRepository
import com.datienza.spacex.hiltbinder.ContributesBinding
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ContributesBinding(SingletonComponent::class)
class GetLaunchesUseCaseImpl @Inject constructor(
    private val repository: LaunchesRepository,
) : GetLaunchesUseCase {

    override fun invoke(rocketId: String): Single<List<Launch>> =
        repository.getLaunches(rocketId)
}
