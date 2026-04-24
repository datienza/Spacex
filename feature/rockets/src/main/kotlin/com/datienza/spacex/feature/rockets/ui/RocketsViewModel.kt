package com.datienza.spacex.feature.rockets.ui

import androidx.lifecycle.viewModelScope
import com.datienza.spacex.core.common.viewmodel.BaseViewModel
import com.datienza.spacex.core.common.viewmodel.NavigationIntent
import com.datienza.spacex.core.common.viewmodel.UiEvent
import com.datienza.spacex.core.common.viewmodel.UiState
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.data.rockets.usecase.GetRocketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
) : BaseViewModel<RocketsViewModel.State, RocketsViewModel.Event, RocketsViewModel.NavIntent>(
    State()
) {

    init { onEvent(Event.LoadRockets) }

    fun onEvent(event: Event) {
        when (event) {
            Event.LoadRockets -> loadRockets()
            is Event.FilterByActive -> filterByActive(event.activeOnly)
            is Event.RocketClicked -> navigate(
                NavIntent.GoToRocketInfo(event.rocket.id, event.rocket.description),
            )
        }
    }

    private fun loadRockets() {
        updateState { copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            runCatching { getRocketsUseCase() }
                .onSuccess { rockets ->
                    updateState { copy(isLoading = false, rockets = rockets, allRockets = rockets) }
                }
                .onFailure {
                    updateState { copy(isLoading = false, isError = true) }
                }
        }
    }

    private fun filterByActive(activeOnly: Boolean) {
        updateState {
            val filtered = if (activeOnly) allRockets.filter { it.active } else allRockets
            copy(rockets = filtered, activeOnly = activeOnly)
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val rockets: List<Rocket> = emptyList(),
        val allRockets: List<Rocket> = emptyList(),
        val activeOnly: Boolean = false,
    ) : UiState

    sealed interface Event : UiEvent {
        data object LoadRockets : Event
        data class FilterByActive(val activeOnly: Boolean) : Event
        data class RocketClicked(val rocket: Rocket) : Event
    }

    sealed interface NavIntent : NavigationIntent {
        data class GoToRocketInfo(val rocketId: String, val rocketDescription: String) : NavIntent
    }
}