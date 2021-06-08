package com.datienza.spacex.domain.repository

import com.datienza.spacex.domain.model.Launch
import io.reactivex.rxjava3.core.Single

interface LaunchesRepository {

    fun getLaunches(rocketId: String): Single<List<Launch>>

}