package com.datienza.spacex.domain.usecase

import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.domain.repository.LaunchesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class GetLaunchesUseCaseImplTest {

    private companion object {
        const val ROCKET_ID = "rocket-Id"
    }

    private val launchesRepository = mock<LaunchesRepository>()

    private val sut = GetLaunchesUseCaseImpl(launchesRepository)

    @Test
    fun `invoke should return repository response`() {
        val expected = mock<List<Launch>>()
        whenever(launchesRepository.getLaunches(ROCKET_ID)).thenReturn(Single.just(expected))

        val result = sut(ROCKET_ID).test()

        result.assertValue(expected)
    }
}