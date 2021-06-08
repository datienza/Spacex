package com.datienza.spacex.data.repository

import com.datienza.spacex.common.mapper.Mapper
import com.datienza.spacex.data.network.apiservice.LaunchesApiService
import com.datienza.spacex.data.network.model.LaunchResponseDTO
import com.datienza.spacex.data.network.model.LaunchesOptionsDTO
import com.datienza.spacex.data.network.model.LaunchesQueryDTO
import com.datienza.spacex.data.network.model.LaunchesRequestDTO
import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.domain.repository.LaunchesRepository
import io.reactivex.rxjava3.core.Single

class LaunchesRepositoryImpl constructor(
    private val apiService: LaunchesApiService,
    private val mapper: Mapper<LaunchResponseDTO, Launch>,
) : LaunchesRepository {

    override fun getLaunches(rocketId: String): Single<List<Launch>> {
        val launchesRequestDTO = LaunchesRequestDTO(
            LaunchesQueryDTO(rocketId),
            LaunchesOptionsDTO(false)
        )
        return apiService.getLaunches(launchesRequestDTO).map { launchesResponse ->
            launchesResponse.docs.map { mapper.map(it) }
        }
    }
}