package com.datienza.spacex.data.launches.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchResponseDTO(
    @SerialName("id")
    val id: String = "",

    @SerialName("flight_number")
    val flightNumber: Int? = null,

    @SerialName("name")
    val missionName: String? = null,

    @SerialName("date_utc")
    val launchDate: String? = null,

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("details")
    val details: String? = null,

    @SerialName("links")
    val links: LinksResponseDTO? = null,
)
