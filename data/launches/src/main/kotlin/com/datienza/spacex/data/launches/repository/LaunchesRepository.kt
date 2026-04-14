package com.datienza.spacex.data.launches.repository

import com.datienza.spacex.core.model.Launch
import io.reactivex.rxjava3.core.Single

interface LaunchesRepository {
    fun getLaunches(rocketId: String): Single<List<Launch>>
}
