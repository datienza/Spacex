package com.datienza.spacex.domain.model

data class Rocket(
    val id: String,
    val name: String,
    val country: String,
    val active: Boolean,
    val description: String,
    var numEngines: Int,
    var image: String
)
