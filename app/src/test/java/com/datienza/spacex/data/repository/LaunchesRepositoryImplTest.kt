package com.datienza.spacex.data.repository

import com.datienza.spacex.common.mapper.Mapper
import com.datienza.spacex.data.network.apiservice.LaunchesApiService
import com.datienza.spacex.data.network.model.LaunchResponseDTO
import com.datienza.spacex.data.network.model.LaunchesResponseDTO
import com.datienza.spacex.domain.model.Launch
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class LaunchesRepositoryImplTest {

    private companion object {
        const val ROCKET_ID = "rocket-Id"
    }

    private val apiService = mock<LaunchesApiService>()
    private val launchesMapper = mock<Mapper<LaunchResponseDTO, Launch>>()
    private val sut = LaunchesRepositoryImpl(apiService, launchesMapper)

    @Test
    fun `getLaunches should return mapped launches dto from the service`() {
        val launchResponseDTO = mock<LaunchResponseDTO>()
        val launchesResponseDTO = mock<LaunchesResponseDTO> {
            on { docs } doReturn listOf(launchResponseDTO)
        }
        val launchResponse = mock<Launch>()
        whenever(apiService.getLaunches(isA())).thenReturn(Single.just(launchesResponseDTO))
        whenever(launchesMapper.map(launchResponseDTO)).thenReturn(launchResponse)

        val result = sut.getLaunches(ROCKET_ID).test()

        result.assertValue(listOf(launchResponse))
    }

    @Test
    fun `getLaunches should return result failure when service returns failure`() {
        val error = mock<Throwable>()
        whenever(apiService.getLaunches(isA())).thenReturn(Single.error(error))

        val result = sut.getLaunches(ROCKET_ID).test()

        result.assertError(error)
    }
}