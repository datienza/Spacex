package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket
import io.reactivex.rxjava3.core.Single

interface GetRocketsUseCase {
    operator fun invoke(): Single<List<Rocket>>
}
