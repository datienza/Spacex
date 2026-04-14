package com.datienza.spacex.core.common.viewmodel

/**
 * Marker interface for one-shot navigation commands emitted by a [BaseViewModel].
 *
 * Each emission is delivered **exactly once** to a single collector via the
 * [kotlinx.coroutines.channels.Channel] backing in [BaseViewModel], preventing
 * duplicate navigation on configuration change.
 *
 * ```kotlin
 * sealed interface RocketsNavIntent : NavigationIntent {
 *     data class GoToRocketInfo(val rocketId: String) : RocketsNavIntent
 *     data object GoBack : RocketsNavIntent
 * }
 * ```
 */
interface NavigationIntent

interface Navigator<N : NavigationIntent> {
    suspend fun navigate(navigationIntent: N)
}
