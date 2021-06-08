package com.datienza.spacex.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.datienza.spacex.common.RxSynchronizeSchedulersRule
import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.domain.usecase.GetLaunchesUseCase
import com.datienza.spacex.presentation.model.LaunchItemContent
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RocketInfoViewModelTest {

    private companion object {
        const val ROCKET_ID = "rocket-Id"
        const val LAUNCH_YEAR = 2006
    }
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var rxJavaRule: TestRule = RxSynchronizeSchedulersRule()

    private val getLaunchesUseCase = mock<GetLaunchesUseCase>()

    private val sut = RocketInfoViewModel(getLaunchesUseCase)

    @Test
    fun `getData SHOULD return content state WHEN use case returns the list of rockets`() {
        val launch = mock<Launch>() {
            on { launchYear } doReturn LAUNCH_YEAR
        }
        val response = listOf(launch)
        whenever(getLaunchesUseCase(ROCKET_ID)).doReturn(Single.just(response))

        sut.getData(ROCKET_ID)

        val observer = sut.viewState.test()
        val launchesPerYearMap = mapOf(LAUNCH_YEAR to listOf(launch))
        val launchItemContentList = listOf(LaunchItemContent.Header(LAUNCH_YEAR), LaunchItemContent.Item(launch))
        observer.assertValue(RocketInfoViewModel.State.Content(launchesPerYearMap, launchItemContentList))
    }

    @Test
    fun `getData SHOULD return error state WHEN use case returns error`() {
        val response = Throwable()
        whenever(getLaunchesUseCase(ROCKET_ID)).doReturn(Single.error(response))

        sut.getData(ROCKET_ID)

        val observer = sut.viewState.test()
        observer.assertValue(RocketInfoViewModel.State.Error)
    }

}