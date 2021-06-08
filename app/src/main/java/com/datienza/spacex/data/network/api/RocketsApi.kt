package com.datienza.spacex.data.network.api

import com.datienza.spacex.data.network.model.RocketResponseDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketsApi {

    @GET("v4/rockets")
    fun getRockets(): Single<List<RocketResponseDTO>>

    @GET("v4/rockets/{rocket_id}")
    fun getRocket(@Path("rocket_id") rocketId: String): Single<RocketResponseDTO>
}
