package com.datienza.spacex.data.launches.usecase

import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.repository.LaunchesRepository
import io.reactivex.rxjava3.core.Single
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Test

class GetLaunchesUseCaseImplTest {

    private companion object {
        const val ROCKET_ID = "rocket-Id"
    }

    private val repository = mock<LaunchesRepository>()
    private val sut        = GetLaunchesUseCaseImpl(repository)

    @Test
    fun `invoke should return repository response`() {
        val expected = mock<List<Launch>>()
        whenever(repository.getLaunches(ROCKET_ID)).thenReturn(Single.just(expected))

        val result = sut(ROCKET_ID).test()

        result.assertValue(expected)
    }
}
