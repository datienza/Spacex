package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesRequestDTO(
    @SerialName("query")
    val query: LaunchesQueryDTO,

    @SerialName("options")
    val options: LaunchesOptionsDTO,
)
