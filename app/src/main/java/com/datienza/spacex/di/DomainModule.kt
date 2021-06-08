package com.datienza.spacex.di

import com.datienza.spacex.domain.usecase.GetLaunchesUseCase
import com.datienza.spacex.domain.usecase.GetLaunchesUseCaseImpl
import com.datienza.spacex.domain.usecase.GetRocketsUseCase
import com.datienza.spacex.domain.usecase.GetRocketsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindsGetLaunchesUseCase(getLaunchesUseCaseImpl: GetLaunchesUseCaseImpl): GetLaunchesUseCase

    @Binds
    fun bindsGetRocketsUseCase(getRocketsUseCaseImpl: GetRocketsUseCaseImpl): GetRocketsUseCase
}