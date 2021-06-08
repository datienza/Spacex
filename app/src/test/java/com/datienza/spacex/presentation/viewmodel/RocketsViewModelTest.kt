package com.datienza.spacex.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.datienza.spacex.common.RxSynchronizeSchedulersRule
import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.usecase.GetRocketsUseCase
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RocketsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var rxJavaRule: TestRule = RxSynchronizeSchedulersRule()

    private val getRocketsUseCase = mock<GetRocketsUseCase>()

    private val sut = RocketsViewModel(getRocketsUseCase)

    @Test
    fun `getData SHOULD return content state WHEN use case returns the list of rockets`() {
        val activeRocket = mock<Rocket> {
            on { active } doReturn true
        }
        val response = listOf(activeRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()

        val observer = sut.viewState.test()
        observer.assertValue(RocketsViewModel.State.Content(response))
    }

    @Test
    fun `getData SHOULD return error state WHEN use case returns error`() {
        val response = Throwable()
        whenever(getRocketsUseCase()).doReturn(Single.error(response))

        sut.getData()

        val observer = sut.viewState.test()
        observer.assertValue(RocketsViewModel.State.Error)
    }

    @Test
    fun `filterData SHOULD return active rockets WHEN active is true`() {
        val activeRocket = mock<Rocket> {
            on { active } doReturn true
        }
        val inactiveRocket = mock<Rocket> {
            on { active } doReturn false
        }
        val response = listOf(activeRocket, inactiveRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()
        sut.filterData(true)

        val observer = sut.viewState.test()
        observer.assertValue(RocketsViewModel.State.Content(listOf(activeRocket)))
    }

    @Test
    fun `filterData SHOULD return all rockets rockets WHEN active is false`() {
        val activeRocket = mock<Rocket> {
            on { active } doReturn true
        }
        val inactiveRocket = mock<Rocket> {
            on { active } doReturn false
        }
        val response = listOf(activeRocket, inactiveRocket)
        whenever(getRocketsUseCase()).doReturn(Single.just(response))

        sut.getData()
        sut.filterData(false)

        val observer = sut.viewState.test()
        observer.assertValue(RocketsViewModel.State.Content(response))
    }

}