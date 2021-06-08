package com.datienza.spacex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.datienza.spacex.domain.model.Rocket
import com.datienza.spacex.domain.usecase.GetRocketsUseCase
import com.datienza.spacex.presentation.viewmodel.common.DisposingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase
): DisposingViewModel() {

    private val _viewState: MutableLiveData<State> = MutableLiveData(State.Loading)
    val viewState: LiveData<State> = _viewState

    private var rocketsList = mutableListOf<Rocket>()

    fun getData() {
        getRocketsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _viewState.postValue(State.Loading) }
            .subscribe ({
                setRocketsList(it)
                _viewState.postValue(State.Content(it))
            }, {
                _viewState.postValue(State.Error)
            })
            .addTo(compositeDisposable)
    }

    private fun setRocketsList(rockets: List<Rocket>?) {
        rockets?.let {
            if (rocketsList.isNotEmpty()) {
                rocketsList.clear()
            }
            rocketsList.addAll(rockets)
        }
    }

    fun filterData(active: Boolean) {
        if (rocketsList.isNotEmpty()) {
            val filteredRockets = if (active) {
                rocketsList.filter { it.active }
            } else {
                rocketsList
            }
            _viewState.postValue(State.Content(filteredRockets))
        }
    }

    sealed class State {
        object Loading : State()
        data class Content(val rockets: List<Rocket>): State()
        object Error : State()
    }

}