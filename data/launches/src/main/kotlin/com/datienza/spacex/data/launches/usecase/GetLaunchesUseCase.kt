package com.datienza.spacex.data.launches.usecase

import com.datienza.spacex.core.model.Launch
import io.reactivex.rxjava3.core.Single

interface GetLaunchesUseCase {
    operator fun invoke(rocketId: String): Single<List<Launch>>
}
