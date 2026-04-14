package com.datienza.spacex.data.rockets.di

import com.datienza.spacex.core.common.mapper.ListMapperImpl
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.api.RocketsApi
import com.datienza.spacex.data.rockets.mapper.RocketResponseMapper
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import com.datienza.spacex.data.rockets.repository.RocketsRepository
import com.datienza.spacex.data.rockets.repository.RocketsRepositoryImpl
import com.datienza.spacex.data.rockets.usecase.GetRocketsUseCase
import com.datienza.spacex.data.rockets.usecase.GetRocketsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RocketsDataModule {

    @Binds
    @Singleton
    fun bindsRocketsRepository(impl: RocketsRepositoryImpl): RocketsRepository

    @Binds
    fun bindsGetRocketsUseCase(impl: GetRocketsUseCaseImpl): GetRocketsUseCase

    companion object {

        @Provides
        @Singleton
        fun provideRocketsApi(retrofit: Retrofit): RocketsApi =
            retrofit.create(RocketsApi::class.java)

        @Provides
        fun provideRocketListMapper(mapper: RocketResponseMapper): ListMapperImpl<RocketResponseDTO, Rocket> =
            ListMapperImpl(mapper)
    }
}
