package com.datienza.spacex.data.launches.usecase

import com.datienza.spacex.core.model.Launch

interface GetLaunchesUseCase {
    suspend operator fun invoke(rocketId: String): List<Launch>
}
