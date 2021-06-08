package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class LaunchResponseDTO(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("flight_number")
    @Expose
    val flightNumber: Int?,

    @SerializedName("name")
    @Expose
    val missionName: String?,

    @SerializedName("date_utc")
    @Expose
    val launchDate: String?,

    @SerializedName("success")
    @Expose
    val success: Boolean?,

    @SerializedName("details")
    @Expose
    val details: String?,

    @SerializedName("links")
    @Expose
    val links: LinksResponseDTO?

)