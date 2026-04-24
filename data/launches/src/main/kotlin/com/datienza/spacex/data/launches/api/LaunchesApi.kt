package com.datienza.spacex.data.launches.api

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.mapper.LaunchResponseMapper
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.model.LaunchesRequestDTO
import com.datienza.spacex.data.launches.model.LaunchesResponseDTO
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

interface LaunchesApi {

    @POST("v4/launches/query")
    suspend fun getLaunches(@Body query: LaunchesRequestDTO): LaunchesResponseDTO
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LaunchesDataModule {

    @Binds
    abstract fun bindLaunchMapper(impl: LaunchResponseMapper): Mapper<LaunchResponseDTO, Launch>

    companion object {
        @Provides
        @Singleton
        fun provideLaunchesApi(retrofit: Retrofit): LaunchesApi = retrofit.create()
    }
}