package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.api.RocketsApi
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.datienza.spacex.domain.model.Rocket
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RocketsApiServiceImpl @Inject constructor(
    private val api: RocketsApi
) : RocketsApiService {

    override fun getRockets(): Single<List<RocketResponseDTO>> =
        api.getRockets()
}