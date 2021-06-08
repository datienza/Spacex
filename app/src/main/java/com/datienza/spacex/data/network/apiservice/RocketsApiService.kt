package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.model.RocketResponseDTO
import io.reactivex.rxjava3.core.Single

interface RocketsApiService {
    fun getRockets(): Single<List<RocketResponseDTO>>
}