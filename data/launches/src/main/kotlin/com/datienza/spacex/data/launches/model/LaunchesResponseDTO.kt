package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesResponseDTO(
    @SerialName("docs")
    val docs: List<LaunchResponseDTO> = emptyList(),
)
