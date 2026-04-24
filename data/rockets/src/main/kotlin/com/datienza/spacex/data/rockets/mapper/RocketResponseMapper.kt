package com.datienza.spacex.data.rockets.mapper

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import javax.inject.Inject

class RocketResponseMapper @Inject constructor() : Mapper<RocketResponseDTO, Rocket> {
    override fun map(input: RocketResponseDTO): Rocket = Rocket(
        id          = input.id.orEmpty(),
        name        = input.name.orEmpty(),
        country     = input.country.orEmpty(),
        active      = input.active ?: false,
        description = input.description.orEmpty(),
        numEngines  = input.engines?.number ?: 0,
        image       = input.images?.firstOrNull().orEmpty(),
    )
}
