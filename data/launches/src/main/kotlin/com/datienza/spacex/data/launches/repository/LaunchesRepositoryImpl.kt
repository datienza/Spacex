package com.datienza.spacex.data.launches.repository

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.api.LaunchesApi
import com.datienza.spacex.data.launches.mapper.LaunchResponseMapper
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.model.LaunchesOptionsDTO
import com.datienza.spacex.data.launches.model.LaunchesQueryDTO
import com.datienza.spacex.data.launches.model.LaunchesRequestDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val api: LaunchesApi,
    private val mapper: Mapper<LaunchResponseDTO, Launch>,
) : LaunchesRepository {

    override fun getLaunches(rocketId: String): Single<List<Launch>> {
        val request = LaunchesRequestDTO(
            query   = LaunchesQueryDTO(rocket = rocketId),
            options = LaunchesOptionsDTO(pagination = false),
        )
        return api.getLaunches(request).map { response ->
            response.docs.map { mapper.map(it) }
        }
    }
}
