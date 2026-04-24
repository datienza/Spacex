package com.datienza.spacex.data.rockets.api

import com.datienza.spacex.core.common.mapper.ListMapperImpl
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.mapper.RocketResponseMapper
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

interface RocketsApi {

    @GET("v4/rockets")
    suspend fun getRockets(): List<RocketResponseDTO>

    @GET("v4/rockets/{rocket_id}")
    suspend fun getRocket(@Path("rocket_id") rocketId: String): RocketResponseDTO
}


@Module
@InstallIn(SingletonComponent::class)
object RocketsDataModule {

    @Provides
    @Singleton
    fun provideRocketsApi(retrofit: Retrofit): RocketsApi =
        retrofit.create(RocketsApi::class.java)

    @Provides
    fun provideRocketListMapper(mapper: RocketResponseMapper): ListMapperImpl<RocketResponseDTO, Rocket> =
        ListMapperImpl(mapper)
}
