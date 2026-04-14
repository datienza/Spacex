package com.datienza.spacex.feature.rocketinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datienza.spacex.core.common.viewmodel.DisposingViewModel
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.usecase.GetLaunchesUseCase
import com.datienza.spacex.feature.rocketinfo.model.LaunchItemContent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class RocketInfoViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
) : DisposingViewModel() {

    private val _viewState = MutableLiveData<State>(State.Loading)
    val viewState: LiveData<State> = _viewState

    fun getData(rocketId: String) {
        getLaunchesUseCase(rocketId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _viewState.postValue(State.Loading) }
            .subscribe(
                { launches ->
                    val grouped = launches.sortedBy { it.launchYear }.groupBy { it.launchYear }
                    _viewState.postValue(State.Content(grouped, buildItemsList(grouped)))
                },
                { _viewState.postValue(State.Error) },
            )
            .addTo(compositeDisposable)
    }

    private fun buildItemsList(grouped: Map<Int, List<Launch>>): List<LaunchItemContent> =
        mutableListOf<LaunchItemContent>().also { list ->
            grouped.forEach { (year, items) ->
                list.add(LaunchItemContent.Header(year))
                list.addAll(items.map { LaunchItemContent.Item(it) })
            }
        }

    sealed class State {
        object Loading : State()
        data class Content(
            val launchesPerYearMap: Map<Int, List<Launch>>,
            val itemsList: List<LaunchItemContent>,
        ) : State()
        object Error : State()
    }
}
