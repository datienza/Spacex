package com.datienza.spacex.domain.model

import java.util.*

data class Launch(
    val flightNumber: Int,
    val missionName: String,
    val launchYear: Int,
    val launchDate: Date,
    val launchSuccess: Boolean,
    val details: String,
    val mission_patch: String
)
