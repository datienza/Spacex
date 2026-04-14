package com.datienza.spacex.data.launches.repository

import com.datienza.spacex.core.common.mapper.Mapper
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.api.LaunchesApi
import com.datienza.spacex.data.launches.model.LaunchResponseDTO
import com.datienza.spacex.data.launches.model.LaunchesResponseDTO
import io.reactivex.rxjava3.core.Single
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.isA
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Test

class LaunchesRepositoryImplTest {

    private companion object {
        const val ROCKET_ID = "rocket-Id"
    }

    private val api    = mock<LaunchesApi>()
    private val mapper = mock<Mapper<LaunchResponseDTO, Launch>>()
    private val sut    = LaunchesRepositoryImpl(api, mapper)

    @Test
    fun `getLaunches should return mapped launches from api`() {
        val dto      = mock<LaunchResponseDTO>()
        val response = mock<LaunchesResponseDTO> { on { docs } doReturn listOf(dto) }
        val launch   = mock<Launch>()
        whenever(api.getLaunches(isA())).thenReturn(Single.just(response))
        whenever(mapper.map(dto)).thenReturn(launch)

        val result = sut.getLaunches(ROCKET_ID).test()

        result.assertValue(listOf(launch))
    }

    @Test
    fun `getLaunches should return failure when api fails`() {
        val error = mock<Throwable>()
        whenever(api.getLaunches(isA())).thenReturn(Single.error(error))

        val result = sut.getLaunches(ROCKET_ID).test()

        result.assertError(error)
    }
}
