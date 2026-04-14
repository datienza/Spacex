package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksResponseDTO(
    @SerialName("patch")
    val patch: PatchResponseDTO? = null,
)
