package com.datienza.spacex.data.rockets.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnginesResponseDTO(
    @SerialName("number")
    val number: Int? = null,
)
