package com.datienza.spacex.data.rockets.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketResponseDTO(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("active")
    val active: Boolean? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("engines")
    val engines: EnginesResponseDTO? = null,

    @SerialName("flickr_images")
    val images: List<String>? = null,
)
