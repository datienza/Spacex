package com.datienza.spacex.data.launches.repository

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.api.LaunchesApi
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.model.LaunchesOptionsDTO
import com.datienza.spacex.data.launches.model.LaunchesQueryDTO
import com.datienza.spacex.data.launches.model.LaunchesRequestDTO
import com.datienza.spacex.hiltbinder.ContributesBinding
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@ContributesBinding(SingletonComponent::class)
@Singleton
class LaunchesRepositoryImpl @Inject constructor(
    private val api: LaunchesApi,
    private val mapper: Mapper<LaunchResponseDTO, Launch>,
) : LaunchesRepository {

    override suspend fun getLaunches(rocketId: String): List<Launch> {
        val request = LaunchesRequestDTO(
            query   = LaunchesQueryDTO(rocket = rocketId),
            options = LaunchesOptionsDTO(pagination = false),
        )
        return api.getLaunches(request).docs.map { mapper.map(it) }
    }
}
