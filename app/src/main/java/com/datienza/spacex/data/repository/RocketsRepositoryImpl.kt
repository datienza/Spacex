package com.datienza.spacex.data.repository

import com.datienza.spacex.common.mapper.ListMapperImpl
import com.datienza.spacex.data.network.apiservice.RocketsApiService
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.repository.RocketsRepository
import io.reactivex.rxjava3.core.Single

class RocketsRepositoryImpl constructor(
    private val apiService: RocketsApiService,
    private val mapper: ListMapperImpl<RocketResponseDTO, Rocket>,
): RocketsRepository {

    override fun getRockets(): Single<List<Rocket>> {
        return apiService.getRockets().map { mapper.map(it) }
    }
}