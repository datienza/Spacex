package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LaunchesRequestDTO(
    @SerializedName("query")
    @Expose
    var query: LaunchesQueryDTO,

    @SerializedName("options")
    @Expose
    var options: LaunchesOptionsDTO,
)