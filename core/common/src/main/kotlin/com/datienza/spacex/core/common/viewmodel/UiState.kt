package com.datienza.spacex.core.common.viewmodel

/**
 * Marker interface for all UI state classes.
 *
 * Implementations should be **immutable data classes** so that equality checks
 * performed by [kotlinx.coroutines.flow.StateFlow] correctly suppress duplicate
 * emissions and the UI only re-renders on genuine changes.
 *
 * ```kotlin
 * data class RocketsState(
 *     val isLoading: Boolean = false,
 *     val rockets: List<Rocket> = emptyList(),
 * ) : UiState
 * ```
 */
interface UiState
