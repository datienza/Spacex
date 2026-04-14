package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesOptionsDTO(
    @SerialName("pagination")
    val pagination: Boolean,
)
