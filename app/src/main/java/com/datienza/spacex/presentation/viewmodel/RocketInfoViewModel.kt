package com.datienza.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.usecase.GetLaunchesUseCase
import com.datienza.spacex.presentation.model.LaunchItemContent
import com.datienza.spacex.presentation.viewmodel.common.DisposingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class RocketInfoViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : DisposingViewModel() {

    private val _viewState: MutableLiveData<State> = MutableLiveData(State.Loading)

    val viewState: LiveData<State> = _viewState

    fun getData(rocketId: String) {
        getLaunchesUseCase(rocketId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _viewState.postValue(State.Loading) }
            .subscribe({ response ->
                val launchesPerYearMap =
                    response.sortedBy { it.launchYear }.groupBy { it.launchYear }
                _viewState.postValue(
                    State.Content(
                        launchesPerYearMap,
                        getLaunchesItemsList(launchesPerYearMap)
                    )
                )
            }, {
                _viewState.postValue(State.Error)
            })
            .addTo(compositeDisposable)
    }

    private fun getLaunchesItemsList(
        launchesPerYearMap: Map<Int, List<Launch>>
    ): List<LaunchItemContent> {
        val launchesItemsList = mutableListOf<LaunchItemContent>()
        launchesPerYearMap.forEach { entry ->
            launchesItemsList.add(LaunchItemContent.Header(entry.key))
            launchesItemsList.addAll(entry.value.map { LaunchItemContent.Item(it) })
        }
        return launchesItemsList
    }

    sealed class State {
        object Loading : State()
        data class Content(
            val launchesPerYearMap: Map<Int, List<Launch>>,
            val itemsList: List<LaunchItemContent>
        ) : State()

        object Error : State()
    }

}