package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.api.LaunchesApi
import com.datienza.spacex.data.network.model.LaunchesRequestDTO
import com.datienza.spacex.data.network.model.LaunchesResponseDTO
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class LaunchesApiServiceImplTest {

    private val api = mock<LaunchesApi>()
    private val sut = LaunchesApiServiceImpl(api)

    @Test
    fun `getLaunches SHOULD return success with api result WHEN it is successful`() {
        val requestDTO = mock<LaunchesRequestDTO>()
        val result = mock<LaunchesResponseDTO>()
        whenever(api.getLaunches(requestDTO)).thenReturn(Single.just(result))

        val testObserver = sut.getLaunches(requestDTO).test()

        testObserver.assertValue(result)
    }

    @Test
    fun `getLaunches SHOULD return failure with api error WHEN it is error`() {
        val requestDTO = mock<LaunchesRequestDTO>()
        val result = mock<Throwable>()
        whenever(api.getLaunches(requestDTO)).thenReturn(Single.error(result))

        val testObserver = sut.getLaunches(requestDTO).test()

        testObserver.assertError(result)
    }
}