package com.datienza.spacex.data.network.mapper

import com.datienza.spacex.common.mapper.Mapper
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.datienza.spacex.domain.model.Rocket

class RocketResponseMapper : Mapper<RocketResponseDTO, Rocket> {
    override fun map(input: RocketResponseDTO): Rocket {
        return Rocket(
            input.id.orEmpty(),
            input.name.orEmpty(),
            input.country.orEmpty(),
            input.active ?: false,
            input.description.orEmpty(),
            input.engines?.number ?: 0,
            input.images?.get(0).orEmpty()
        )
    }
}