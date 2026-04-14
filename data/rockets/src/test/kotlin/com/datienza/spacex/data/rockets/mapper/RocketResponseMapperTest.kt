package com.datienza.spacex.data.rockets.mapper

import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.model.EnginesResponseDTO
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RocketResponseMapperTest {

    private companion object {
        const val ID          = "rocket-id"
        const val NAME        = "name"
        const val COUNTRY     = "country"
        const val DESCRIPTION = "description"
        const val NUM_ENGINES = 1
        const val IMAGE       = "http://spacex.com/image"
    }

    private val sut = RocketResponseMapper()

    @Test
    fun `map SHOULD return mapped Rocket`() {
        val input = RocketResponseDTO(
            id          = ID,
            name        = NAME,
            country     = COUNTRY,
            active      = true,
            description = DESCRIPTION,
            engines     = EnginesResponseDTO(NUM_ENGINES),
            images      = listOf(IMAGE),
        )
        val expected = Rocket(
            id          = ID,
            name        = NAME,
            country     = COUNTRY,
            active      = true,
            description = DESCRIPTION,
            numEngines  = NUM_ENGINES,
            image       = IMAGE,
        )

        val result = sut.map(input)

        assertThat(result).isEqualTo(expected)
    }
}
