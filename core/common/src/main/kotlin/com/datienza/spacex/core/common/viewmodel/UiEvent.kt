package com.datienza.spacex.core.common.viewmodel

/**
 * Marker interface for one-shot side-effects directed at the View layer.
 *
 * Typical examples: showing a Snackbar, a Toast, or a Dialog.
 * Each emission is delivered **exactly once** to a single collector via the
 * [kotlinx.coroutines.channels.Channel] backing in [BaseViewModel].
 *
 * ```kotlin
 * sealed interface RocketsEvent : UiEvent {
 *     data class ShowError(val message: String) : RocketsEvent
 *     data object RefreshComplete : RocketsEvent
 * }
 * ```
 */
interface UiEvent
