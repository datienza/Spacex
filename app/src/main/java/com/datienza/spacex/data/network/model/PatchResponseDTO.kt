package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class PatchResponseDTO(
    @SerializedName("small")
    @Expose
    var small: String?,

    @SerializedName("large")
    @Expose
    var large: String?,
)