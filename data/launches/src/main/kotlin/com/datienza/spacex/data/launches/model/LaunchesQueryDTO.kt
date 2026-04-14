package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesQueryDTO(
    @SerialName("rocket")
    val rocket: String,
)
