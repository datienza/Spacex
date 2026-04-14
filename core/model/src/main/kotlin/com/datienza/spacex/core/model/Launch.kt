package com.datienza.spacex.core.model

import java.util.Date

data class Launch(
    val flightNumber: Int,
    val missionName: String,
    val launchYear: Int,
    val launchDate: Date,
    val launchSuccess: Boolean,
    val details: String,
    val missionPatch: String,
)
