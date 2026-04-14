package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchResponseDTO(
    @SerialName("small")
    val small: String? = null,

    @SerialName("large")
    val large: String? = null,
)
