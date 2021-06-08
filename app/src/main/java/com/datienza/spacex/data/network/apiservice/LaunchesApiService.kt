package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.model.LaunchesRequestDTO
import com.datienza.spacex.data.network.model.LaunchesResponseDTO
import io.reactivex.rxjava3.core.Single

interface LaunchesApiService {
    fun getLaunches(launchesRequestDTO: LaunchesRequestDTO): Single<LaunchesResponseDTO>
}