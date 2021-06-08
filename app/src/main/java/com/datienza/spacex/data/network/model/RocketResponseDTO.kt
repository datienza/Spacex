package com.datienza.spacex.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RocketResponseDTO(

    @SerializedName("id")
    @Expose
    var id: String?,

    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("country")
    @Expose
    var country: String?,

    @SerializedName("active")
    @Expose
    var active: Boolean?,

    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("engines")
    @Expose
    var engines: EnginesResponseDTO?,

    @SerializedName("flickr_images")
    @Expose
    var images: List<String>?
)