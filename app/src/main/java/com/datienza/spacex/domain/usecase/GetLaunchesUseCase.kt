package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Launch
import io.reactivex.rxjava3.core.Single

interface GetLaunchesUseCase {

    operator fun invoke(rocketId: String): Single<List<Launch>>
}