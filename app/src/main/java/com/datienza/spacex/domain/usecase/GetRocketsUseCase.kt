package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Rocket
import io.reactivex.rxjava3.core.Single

interface GetRocketsUseCase {

    operator fun invoke(): Single<List<Rocket>>
}