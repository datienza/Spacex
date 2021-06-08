package com.datienza.spacex.data.network.mapper

import com.datienza.spacex.data.network.model.LaunchResponseDTO
import com.datienza.spacex.data.network.model.LinksResponseDTO
import com.datienza.spacex.data.network.model.PatchResponseDTO
import com.datienza.spacex.domain.model.Launch
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class LaunchResponseMapperTest  {

    private companion object {
        const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val ID = "launch-id"
        const val FLIGHT_NUMBER = 1
        const val MISSION_NAME = "code"
        const val LAUNCH_DATE = "2006-03-24T22:30:00.000Z"
        const val DETAILS = "details"
        const val IMAGE = "http://spacex.com/image"
    }

    private val sut = LaunchResponseMapper()

    @Test
    fun `map SHOULD return mapped Rocket`() {
        val input = LaunchResponseDTO(
            ID,
            FLIGHT_NUMBER,
            MISSION_NAME,
            LAUNCH_DATE,
            true,
            DETAILS,
            LinksResponseDTO(PatchResponseDTO(IMAGE, IMAGE))
        )
        val expected = Launch(FLIGHT_NUMBER, MISSION_NAME, 2006, getDate(LAUNCH_DATE), true, DETAILS, IMAGE)

        val result = sut.map(input)

        assertThat(result).isEqualTo(expected)
    }

    private fun getDate(launchDate: String): Date {
        val simpleDateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        return simpleDateFormat.parse(launchDate)
    }

}