package com.datienza.spacex.feature.rockets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.datienza.spacex.core.common.RxSynchronizeSchedulersRule
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.usecase.GetRocketsUseCase
import com.datienza.spacex.feature.rockets.viewmodel.RocketsViewModel
import com.jraska.livedata.test
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RocketsViewModelTest {

    @get:Rule var rule: TestRule    = InstantTaskExecutorRule()
    @get:Rule var rxRule: TestRule  = RxSynchronizeSchedulersRule()

    private val getRocketsUseCase = mock<GetRocketsUseCase>()
    private val sut               = RocketsViewModel(getRocketsUseCase)

    @Test
    fun `getData SHOULD return content state WHEN use case returns list`() {
        val activeRocket = mock<Rocket> { on { active } doReturn true }
        val response = listOf(activeRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()

        sut.viewState.test().assertValue(RocketsViewModel.State.Content(response))
    }

    @Test
    fun `getData SHOULD return error state WHEN use case returns error`() {
        whenever(getRocketsUseCase()).doReturn(Single.error(Throwable()))

        sut.getData()

        sut.viewState.test().assertValue(RocketsViewModel.State.Error)
    }

    @Test
    fun `filterData SHOULD return only active rockets WHEN active is true`() {
        val activeRocket   = mock<Rocket> { on { active } doReturn true }
        val inactiveRocket = mock<Rocket> { on { active } doReturn false }
        val response = listOf(activeRocket, inactiveRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()
        sut.filterData(true)

        sut.viewState.test().assertValue(RocketsViewModel.State.Content(listOf(activeRocket)))
    }

    @Test
    fun `filterData SHOULD return all rockets WHEN active is false`() {
        val activeRocket   = mock<Rocket> { on { active } doReturn true }
        val inactiveRocket = mock<Rocket> { on { active } doReturn false }
        val response = listOf(activeRocket, inactiveRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()
        sut.filterData(false)

        sut.viewState.test().assertValue(RocketsViewModel.State.Content(response))
    }
}
