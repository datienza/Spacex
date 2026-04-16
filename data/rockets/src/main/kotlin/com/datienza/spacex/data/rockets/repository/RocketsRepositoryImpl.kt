package com.datienza.spacex.data.rockets.repository

import com.datienza.spacex.core.common.mapper.ListMapperImpl
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.api.RocketsApi
import com.datienza.spacex.data.rockets.mapper.RocketResponseMapper
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import com.datienza.spacex.hiltbinder.ContributesBinding
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@ContributesBinding(SingletonComponent::class)
@Singleton
class RocketsRepositoryImpl @Inject constructor(
    private val api: RocketsApi,
    private val mapper: ListMapperImpl<RocketResponseDTO, Rocket>,
) : RocketsRepository {

    override fun getRockets(): Single<List<Rocket>> =
        api.getRockets().map { mapper.map(it) }
}
