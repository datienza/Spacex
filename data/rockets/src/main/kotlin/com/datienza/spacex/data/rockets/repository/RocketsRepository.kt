package com.datienza.spacex.data.rockets.repository

import com.datienza.spacex.core.model.Rocket
import io.reactivex.rxjava3.core.Single

interface RocketsRepository {
    fun getRockets(): Single<List<Rocket>>
}
