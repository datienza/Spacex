package com.datienza.spacex.data.launches.repository

import com.datienza.spacex.core.model.Launch

interface LaunchesRepository {
    suspend fun getLaunches(rocketId: String): List<Launch>
}
