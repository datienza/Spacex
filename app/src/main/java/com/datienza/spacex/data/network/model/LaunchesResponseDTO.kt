package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class LaunchesResponseDTO(
    @SerializedName("docs")
    @Expose
    var docs: List<LaunchResponseDTO>

)