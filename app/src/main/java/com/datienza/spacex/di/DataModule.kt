package com.datienza.spacex.di

import com.datienza.spacex.common.mapper.ListMapperImpl
import com.datienza.spacex.data.network.api.LaunchesApi
import com.datienza.spacex.data.network.api.RocketsApi
import com.datienza.spacex.data.network.apiservice.LaunchesApiService
import com.datienza.spacex.data.network.apiservice.LaunchesApiServiceImpl
import com.datienza.spacex.data.network.apiservice.RocketsApiService
import com.datienza.spacex.data.network.apiservice.RocketsApiServiceImpl
import com.datienza.spacex.data.network.mapper.LaunchResponseMapper
import com.datienza.spacex.data.network.mapper.RocketResponseMapper
import com.datienza.spacex.data.repository.LaunchesRepositoryImpl
import com.datienza.spacex.data.repository.RocketsRepositoryImpl
import com.datienza.spacex.domain.repository.LaunchesRepository
import com.datienza.spacex.domain.repository.RocketsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module(includes = [DataModule.ProvidesModule::class])
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindsLaunchesApiService(blogsApiServiceImpl: LaunchesApiServiceImpl): LaunchesApiService

    @Binds
    fun bindsRocketsApiService(rocketsApiServiceImpl: RocketsApiServiceImpl): RocketsApiService

    @Module
    @InstallIn(ViewModelComponent::class)
    object ProvidesModule {

        @Provides
        fun providesLaunchesRepository(launchesApiService: LaunchesApiService): LaunchesRepository {
            return LaunchesRepositoryImpl(launchesApiService, LaunchResponseMapper())
        }

        @Provides
        fun providesRocketsRepository(rocketsApiService: RocketsApiService): RocketsRepository {
            return RocketsRepositoryImpl(rocketsApiService, ListMapperImpl(RocketResponseMapper()))
        }

        @Provides
        fun provideLaunchesApi(retrofit: Retrofit): LaunchesApi {
            return retrofit.create(LaunchesApi::class.java)
        }

        @Provides
        fun provideRocketsApi(retrofit: Retrofit): RocketsApi {
            return retrofit.create(RocketsApi::class.java)
        }
    }
}