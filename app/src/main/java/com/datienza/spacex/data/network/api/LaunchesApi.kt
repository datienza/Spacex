package com.datienza.spacex.data.network.api

import com.datienza.spacex.data.network.model.LaunchesRequestDTO
import com.datienza.spacex.data.network.model.LaunchesResponseDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LaunchesApi {

    @POST("v4/launches/query")
    fun getLaunches(@Body query: LaunchesRequestDTO): Single<LaunchesResponseDTO>
}
