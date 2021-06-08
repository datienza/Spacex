package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.api.LaunchesApi
import com.datienza.spacex.data.network.model.LaunchResponseDTO
import com.datienza.spacex.data.network.model.LaunchesRequestDTO
import com.datienza.spacex.data.network.model.LaunchesResponseDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LaunchesApiServiceImpl @Inject constructor(
    private val api: LaunchesApi
) : LaunchesApiService {

    override fun getLaunches(launchesRequestDTO: LaunchesRequestDTO): Single<LaunchesResponseDTO> {
        return api.getLaunches(launchesRequestDTO)
    }
}