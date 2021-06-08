package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.repository.RocketsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetRocketsUseCaseImplTest {

    private val rocketsRepository = mock<RocketsRepository>()

    private val sut = GetRocketsUseCaseImpl(rocketsRepository)

    @Test
    fun `invoke should return repository response`() {
        val expected = mock<List<Rocket>>()
        whenever(rocketsRepository.getRockets()).thenReturn(Single.just(expected))

        val result = sut().test()

        result.assertValue(expected)
    }
}