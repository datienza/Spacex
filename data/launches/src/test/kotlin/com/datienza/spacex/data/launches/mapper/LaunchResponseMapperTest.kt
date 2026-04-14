package com.datienza.spacex.data.launches.mapper

import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.model.LinksResponseDTO
import com.datienza.spacex.data.launches.model.PatchResponseDTO
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class LaunchResponseMapperTest {

    private companion object {
        const val DATE_PATTERN   = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val ID             = "launch-id"
        const val FLIGHT_NUMBER  = 1
        const val MISSION_NAME   = "FalconSat"
        const val LAUNCH_DATE    = "2006-03-24T22:30:00.000Z"
        const val DETAILS        = "details"
        const val IMAGE          = "http://spacex.com/image"
    }

    private val sut = LaunchResponseMapper()

    @Test
    fun `map SHOULD return mapped Launch`() {
        val input = LaunchResponseDTO(
            id           = ID,
            flightNumber = FLIGHT_NUMBER,
            missionName  = MISSION_NAME,
            launchDate   = LAUNCH_DATE,
            success      = true,
            details      = DETAILS,
            links        = LinksResponseDTO(PatchResponseDTO(IMAGE, IMAGE)),
        )
        val expectedDate = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
            .apply { timeZone = TimeZone.getDefault() }
            .parse(LAUNCH_DATE)!!
        val expected = Launch(
            flightNumber  = FLIGHT_NUMBER,
            missionName   = MISSION_NAME,
            launchYear    = 2006,
            launchDate    = expectedDate,
            launchSuccess = true,
            details       = DETAILS,
            missionPatch  = IMAGE,
        )

        val result = sut.map(input)

        assertThat(result).isEqualTo(expected)
    }
}
