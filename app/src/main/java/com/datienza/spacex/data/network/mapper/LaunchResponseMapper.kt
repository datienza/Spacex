package com.datienza.spacex.data.network.mapper

import com.datienza.spacex.common.mapper.Mapper
import com.datienza.spacex.data.network.model.LaunchResponseDTO
import com.datienza.spacex.domain.model.Launch
import java.text.SimpleDateFormat
import java.util.*

class LaunchResponseMapper : Mapper<LaunchResponseDTO, Launch> {

    private companion object {
        const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

    override fun map(input: LaunchResponseDTO): Launch {
        val launchDate = getDate(input.launchDate)
        return Launch(
            input.flightNumber ?: 0,
            input.missionName.orEmpty(),
            getLaunchYear(launchDate),
            launchDate,
            input.success ?: false,
            input.details.orEmpty(),
            input.links?.patch?.small.orEmpty()
        )
    }

    private fun getDate(launchDate: String?): Date {
        return launchDate?.let {
            val simpleDateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).apply {
                timeZone = TimeZone.getDefault()
            }
            simpleDateFormat.parse(launchDate)
        } ?: Date()
    }

    private fun getLaunchYear(launchDate: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = launchDate
        return calendar.get(Calendar.YEAR)
    }
}