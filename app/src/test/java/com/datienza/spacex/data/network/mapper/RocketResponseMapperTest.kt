package com.datienza.spacex.data.network.mapper

import com.datienza.spacex.data.network.model.EnginesResponseDTO
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.datienza.spacex.domain.model.Rocket
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RocketResponseMapperTest {

    private companion object {
        const val ID = "rocket-id"
        const val NAME = "name"
        const val COUNTRY = "country"
        const val DESCRIPTION = "description"
        const val NUM_ENGINES = 1
        const val IMAGE = "http://spacex.com/image"
    }

    private val sut = RocketResponseMapper()

    @Test
    fun `map SHOULD return mapped Rocket`() {
        val input = RocketResponseDTO(
            ID,
            NAME,
            COUNTRY,
            true,
            DESCRIPTION,
            EnginesResponseDTO(NUM_ENGINES),
            listOf(IMAGE)
        )
        val expected = Rocket(ID, NAME, COUNTRY, true, DESCRIPTION, NUM_ENGINES, IMAGE)

        val result = sut.map(input)

        assertThat(result).isEqualTo(expected)
    }
}