package com.datienza.spacex.data.launches.di

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.api.LaunchesApi
import com.datienza.spacex.data.launches.mapper.LaunchResponseMapper
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.repository.LaunchesRepository
import com.datienza.spacex.data.launches.repository.LaunchesRepositoryImpl
import com.datienza.spacex.data.launches.usecase.GetLaunchesUseCase
import com.datienza.spacex.data.launches.usecase.GetLaunchesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LaunchesDataModule {

    @Binds
    @Singleton
    fun bindsLaunchesRepository(impl: LaunchesRepositoryImpl): LaunchesRepository

    @Binds
    fun bindsGetLaunchesUseCase(impl: GetLaunchesUseCaseImpl): GetLaunchesUseCase

    @Binds
    fun bindsLaunchResponseMapper(impl: LaunchResponseMapper): Mapper<LaunchResponseDTO, Launch>

    companion object {

        @Provides
        @Singleton
        fun provideLaunchesApi(retrofit: Retrofit): LaunchesApi =
            retrofit.create(LaunchesApi::class.java)
    }
}
