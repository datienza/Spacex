package com.datienza.spacex.data.rockets.repository

import com.datienza.spacex.core.model.Rocket

interface RocketsRepository {
    suspend fun getRockets(): List<Rocket>
}
