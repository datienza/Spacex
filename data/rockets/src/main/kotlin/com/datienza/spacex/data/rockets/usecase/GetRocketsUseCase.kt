package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket

interface GetRocketsUseCase {
    suspend operator fun invoke(): List<Rocket>
}
