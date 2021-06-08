package com.datienza.spacex.data.repository

import com.datienza.spacex.common.mapper.ListMapperImpl
import com.datienza.spacex.data.network.apiservice.RocketsApiService
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.datienza.spacex.domain.model.Rocket
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RocketsRepositoryImplTest {

    private val apiService = mock<RocketsApiService>()
    private val rocketsMapper = mock<ListMapperImpl<RocketResponseDTO, Rocket>>()
    private val sut = RocketsRepositoryImpl(apiService, rocketsMapper)

    @Test
    fun `getRockets should return mapped launches dto from the service`() {
        val rocketResponseDTO = mock<RocketResponseDTO>()
        val rocketsResponseDTO = listOf(rocketResponseDTO)
        val rocketResponse = mock<Rocket>()
        whenever(apiService.getRockets()).thenReturn(Single.just(listOf(rocketResponseDTO)))
        whenever(rocketsMapper.map(rocketsResponseDTO)).thenReturn(listOf(rocketResponse))

        val result = sut.getRockets().test()

        result.assertValue(listOf(rocketResponse))
    }

    @Test
    fun `getRockets should return result failure when service returns failure`() {
        val error = mock<Throwable>()
        whenever(apiService.getRockets()).thenReturn(Single.error(error))

        val result = sut.getRockets().test()

        result.assertError(error)
    }
}