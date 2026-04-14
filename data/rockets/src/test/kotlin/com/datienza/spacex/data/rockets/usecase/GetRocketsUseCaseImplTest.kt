package com.datienza.spacex.data.rockets.usecase

import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.repository.RocketsRepository
import io.reactivex.rxjava3.core.Single
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Test

class GetRocketsUseCaseImplTest {

    private val repository = mock<RocketsRepository>()
    private val sut        = GetRocketsUseCaseImpl(repository)

    @Test
    fun `invoke should return repository response`() {
        val expected = mock<List<Rocket>>()
        whenever(repository.getRockets()).thenReturn(Single.just(expected))

        val result = sut().test()

        result.assertValue(expected)
    }
}
