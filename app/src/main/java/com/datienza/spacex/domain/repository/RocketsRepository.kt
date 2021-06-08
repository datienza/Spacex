package com.datienza.spacex.domain.repository

import com.datienza.spacex.domain.model.Rocket
import io.reactivex.rxjava3.core.Single

interface RocketsRepository {

    fun getRockets(): Single<List<Rocket>>

}