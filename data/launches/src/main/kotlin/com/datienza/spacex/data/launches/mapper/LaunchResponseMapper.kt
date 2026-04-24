package com.datienza.spacex.data.launches.mapper

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class LaunchResponseMapper @Inject constructor() : Mapper<LaunchResponseDTO, Launch> {

    private companion object {
        const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

    override fun map(input: LaunchResponseDTO): Launch {
        val launchDate = parseDate(input.launchDate)
        return Launch(
            flightNumber  = input.flightNumber ?: 0,
            missionName   = input.missionName.orEmpty(),
            launchYear    = getLaunchYear(launchDate),
            launchDate    = launchDate,
            launchSuccess = input.success ?: false,
            details       = input.details.orEmpty(),
            missionPatch  = input.links?.patch?.small.orEmpty(),
        )
    }

    private fun parseDate(launchDate: String?): Date =
        launchDate?.let {
            runCatching {
                SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
                    .apply { timeZone = TimeZone.getDefault() }
                    .parse(it)
            }.getOrNull()
        } ?: Date()

    private fun getLaunchYear(date: Date): Int =
        Calendar.getInstance().also { it.time = date }.get(Calendar.YEAR)
}
