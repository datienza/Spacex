package com.datienza.spacex.data.rockets.repository

import com.datienza.spacex.core.common.mapper.ListMapperImpl
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.api.RocketsApi
import com.datienza.spacex.data.rockets.model.RocketResponseDTO
import io.reactivex.rxjava3.core.Single
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Test

class RocketsRepositoryImplTest {

    private val api    = mock<RocketsApi>()
    private val mapper = mock<ListMapperImpl<RocketResponseDTO, Rocket>>()
    private val sut    = RocketsRepositoryImpl(api, mapper)

    @Test
    fun `getRockets should return mapped result from api`() {
        val dto      = mock<RocketResponseDTO>()
        val dtoList  = listOf(dto)
        val rocket   = mock<Rocket>()
        whenever(api.getRockets()).thenReturn(Single.just(dtoList))
        whenever(mapper.map(dtoList)).thenReturn(listOf(rocket))

        val result = sut.getRockets().test()

        result.assertValue(listOf(rocket))
    }

    @Test
    fun `getRockets should return failure when api fails`() {
        val error = mock<Throwable>()
        whenever(api.getRockets()).thenReturn(Single.error(error))

        val result = sut.getRockets().test()

        result.assertError(error)
    }
}
