package com.datienza.spacex.feature.rocketinfo.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.datienza.spacex.core.common.viewmodel.BaseViewModel
import com.datienza.spacex.core.common.viewmodel.NavigationIntent
import com.datienza.spacex.core.common.viewmodel.UiEvent
import com.datienza.spacex.core.common.viewmodel.UiState
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.data.launches.usecase.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLaunchesUseCase: GetLaunchesUseCase,
) : BaseViewModel<RocketInfoViewModel.State, RocketInfoViewModel.Event, RocketInfoViewModel.NavIntent>(
    State()
) {

    private val rocketId: String = checkNotNull(savedStateHandle["rocketId"])
    val rocketDescription: String = savedStateHandle["rocketDescription"] ?: ""

    init { onEvent(Event.LoadLaunches) }

    fun onEvent(event: Event) {
        when (event) {
            Event.LoadLaunches -> loadLaunches()
            Event.BackClicked -> navigate(NavIntent.GoBack)
        }
    }

    private fun loadLaunches() {
        updateState { copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            runCatching { getLaunchesUseCase(rocketId) }
                .onSuccess { launches ->
                    val grouped = launches
                        .sortedBy { it.launchYear }
                        .groupBy { it.launchYear }
                    updateState {
                        copy(
                            isLoading = false,
                            launchesPerYear = grouped,
                        )
                    }
                }
                .onFailure {
                    updateState { copy(isLoading = false, isError = true) }
                }
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val launchesPerYear: Map<Int, List<Launch>> = emptyMap(),
    ) : UiState

    sealed interface Event : UiEvent {
        data object LoadLaunches : Event
        data object BackClicked : Event
    }

    sealed interface NavIntent : NavigationIntent {
        data object GoBack : NavIntent
    }
}