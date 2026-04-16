package com.datienza.spacex.data.launches.api

import com.datienza.spacex.data.launches.model.LaunchesRequestDTO
import com.datienza.spacex.data.launches.model.LaunchesResponseDTO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

interface LaunchesApi {

    @POST("v4/launches/query")
    fun getLaunches(@Body query: LaunchesRequestDTO): Single<LaunchesResponseDTO>
}

@Module
@InstallIn(SingletonComponent::class)
object LaunchesDataModule {

    @Provides
    @Singleton
    fun provideLaunchesApi(retrofit: Retrofit): LaunchesApi = retrofit.create()
}