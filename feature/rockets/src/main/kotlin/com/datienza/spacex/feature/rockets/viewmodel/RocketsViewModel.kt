package com.datienza.spacex.feature.rockets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datienza.spacex.core.common.viewmodel.DisposingViewModel
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.usecase.GetRocketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
) : DisposingViewModel() {

    private val _viewState = MutableLiveData<State>(State.Loading)
    val viewState: LiveData<State> = _viewState

    private val rocketsList = mutableListOf<Rocket>()

    fun getData() {
        getRocketsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _viewState.postValue(State.Loading) }
            .subscribe(
                { rockets ->
                    rocketsList.clear()
                    rocketsList.addAll(rockets)
                    _viewState.postValue(State.Content(rockets))
                },
                { _viewState.postValue(State.Error) },
            )
            .addTo(compositeDisposable)
    }

    fun filterData(activeOnly: Boolean) {
        if (rocketsList.isNotEmpty()) {
            val filtered = if (activeOnly) rocketsList.filter { it.active } else rocketsList.toList()
            _viewState.postValue(State.Content(filtered))
        }
    }

    sealed class State {
        object Loading : State()
        data class Content(val rockets: List<Rocket>) : State()
        object Error : State()
    }
}
