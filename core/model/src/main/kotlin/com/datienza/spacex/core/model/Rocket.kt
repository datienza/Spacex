package com.datienza.spacex.core.model

data class Rocket(
    val id: String,
    val name: String,
    val country: String,
    val active: Boolean,
    val description: String,
    val numEngines: Int,
    val image: String,
)
