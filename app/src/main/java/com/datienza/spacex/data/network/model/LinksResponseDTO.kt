package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LinksResponseDTO(

    @SerializedName("patch")
    @Expose
    var patch: PatchResponseDTO?
)