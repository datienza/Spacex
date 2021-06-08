package com.datienza.spacex.data.network.apiservice

import com.datienza.spacex.data.network.api.RocketsApi
import com.datienza.spacex.data.network.model.RocketResponseDTO
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RocketsApiServiceImplTest {

    private val api = mock<RocketsApi>()
    private val sut = RocketsApiServiceImpl(api)

    @Test
    fun `getRockets SHOULD return success with api result WHEN it is successful`() {
        val result = mock<List<RocketResponseDTO>>()
        whenever(api.getRockets()).thenReturn(Single.just(result))

        val testObserver = sut.getRockets().test()

        testObserver.assertValue(result)
    }

    @Test
    fun `getRockets SHOULD return failure with api error WHEN it is error`() {
        val result = mock<Throwable>()
        whenever(api.getRockets()).thenReturn(Single.error(result))

        val testObserver = sut.getRockets().test()

        testObserver.assertError(result)
    }
}