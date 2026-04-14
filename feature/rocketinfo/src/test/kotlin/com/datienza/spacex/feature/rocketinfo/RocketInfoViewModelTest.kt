package com.datienza.spacex.feature.rocketinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.datienza.spacex.core.common.RxSynchronizeSchedulersRule
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.usecase.GetLaunchesUseCase
import com.datienza.spacex.feature.rocketinfo.model.LaunchItemContent
import com.datienza.spacex.feature.rocketinfo.viewmodel.RocketInfoViewModel
import com.jraska.livedata.test
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RocketInfoViewModelTest {

    private companion object {
        const val ROCKET_ID  = "rocket-Id"
        const val LAUNCH_YEAR = 2006
    }

    @get:Rule var rule: TestRule   = InstantTaskExecutorRule()
    @get:Rule var rxRule: TestRule = RxSynchronizeSchedulersRule()

    private val getLaunchesUseCase = mock<GetLaunchesUseCase>()
    private val sut                = RocketInfoViewModel(getLaunchesUseCase)

    @Test
    fun `getData SHOULD return content state WHEN use case returns launches`() {
        val launch = mock<Launch> { on { launchYear } doReturn LAUNCH_YEAR }
        whenever(getLaunchesUseCase(ROCKET_ID)).doReturn(Single.just(listOf(launch)))

        sut.getData(ROCKET_ID)

        val launchesPerYearMap = mapOf(LAUNCH_YEAR to listOf(launch))
        val itemsList = listOf(
            LaunchItemContent.Header(LAUNCH_YEAR),
            LaunchItemContent.Item(launch),
        )
        sut.viewState.test()
            .assertValue(RocketInfoViewModel.State.Content(launchesPerYearMap, itemsList))
    }

    @Test
    fun `getData SHOULD return error state WHEN use case returns error`() {
        whenever(getLaunchesUseCase(ROCKET_ID)).doReturn(Single.error(Throwable()))

        sut.getData(ROCKET_ID)

        sut.viewState.test().assertValue(RocketInfoViewModel.State.Error)
    }
}
