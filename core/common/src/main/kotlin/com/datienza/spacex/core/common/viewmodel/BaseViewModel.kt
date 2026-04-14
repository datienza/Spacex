package com.datienza.spacex.core.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel that enforces an MVI contract through three generic contracts:
 *
 * @param UiState        Immutable snapshot of everything the UI needs to render.
 * @param UiEvent        One-shot side-effects directed at the View (e.g. show a Snackbar).
 * @param NavigationIntent  One-shot navigation commands (e.g. go to detail screen).
 *
 * ### Linearised state updates
 * All mutations go through [reducerChannel] — an *unlimited* capacity channel whose
 * items are consumed **sequentially** by a single coroutine.  This guarantees:
 *  - No update is ever dropped (unlimited buffer → [Channel.trySend] never fails).
 *  - Updates are applied in the exact order they were enqueued, with no interleaving.
 *
 * ### One-time channels
 * [uiEvent] and [navigationIntent] are backed by *buffered* [Channel]s exposed as
 * cold [Flow]s via [receiveAsFlow].  Each emission is delivered exactly once to a
 * single collector, so navigation / event logic is never duplicated on re-subscription.
 */
abstract class BaseViewModel<S : UiState, E : UiEvent, N : NavigationIntent>(
    initialState: S,
) : ViewModel() {

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private val _uiState = MutableStateFlow(initialState)

    /** Current UI state — always holds the latest value; safe to read from any thread. */
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    // -------------------------------------------------------------------------
    // One-time channels
    // -------------------------------------------------------------------------

    private val _uiEvent = Channel<E>(Channel.BUFFERED)

    /** Stream of one-time side-effects for the View (Snackbar, Toast, Dialog, …). */
    val uiEvent: Flow<E> = _uiEvent.receiveAsFlow()

    private val _navigationIntent = Channel<N>(Channel.BUFFERED)

    /** Stream of one-time navigation commands. */
    val navigationIntent: Flow<N> = _navigationIntent.receiveAsFlow()

    // -------------------------------------------------------------------------
    // Linearised reducer queue
    // -------------------------------------------------------------------------

    /**
     * Unlimited-capacity channel that queues state-reducer lambdas.
     * A single coroutine drains it sequentially, so callers never need to
     * synchronise and every reducer is guaranteed to see the result of all
     * previously enqueued reducers.
     */
    private val reducerChannel = Channel<S.() -> S>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            reducerChannel.consumeEach { reducer ->
                _uiState.update { currentState -> currentState.reducer() }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Protected API for subclasses
    // -------------------------------------------------------------------------

    /**
     * Enqueue a pure state reducer.  The reducer receives the **current** state
     * and must return the **next** state without mutating its input.
     *
     * Reducers are processed one at a time in FIFO order — it is therefore safe
     * to call this from multiple coroutines concurrently without any additional
     * synchronisation.
     *
     * Always use the lambda receiver (`this`) to derive the next state:
     * ```kotlin
     * updateState { copy(count = count + 1) }   // ✅ always sees latest count
     * ```
     * Never capture [uiState] outside the lambda — that snapshot may be stale
     * by the time the reducer executes:
     * ```kotlin
     * val snap = uiState.value
     * updateState { snap.copy(count = snap.count + 1) }  // ❌ stale snapshot
     * ```
     */
    protected fun updateState(reducer: S.() -> S) {
        // trySend on an UNLIMITED channel never returns a failure result.
        reducerChannel.trySend(reducer)
    }

    /**a
     * Suspends until every reducer currently in the queue has been applied,
     * then calls [block] with the resulting settled state.
     *
     * This is the safe way to **read** state before acting on it asynchronously,
     * because it guarantees you never act on a snapshot that predates pending
     * reducers enqueued by other coroutines:
     * ```kotlin
     * fun onLoginClick() {
     *     viewModelScope.launch {
     *         withState { state ->
     *             if (state.isLoggedIn) navigate(GoToHome)
     *             else sendEvent(ShowLoginError)
     *         }
     *     }
     * }
     * ```
     * Never read [uiState].value directly when subsequent logic depends on
     * the value being up-to-date — use [withState] instead.
     */
    protected suspend fun withState(block: suspend (S) -> Unit) {
        val deferred = CompletableDeferred<S>()
        // Enqueue a no-op reducer that captures the settled state into `deferred`,
        // then returns it unchanged so the actual state is not modified.
        reducerChannel.trySend { also { deferred.complete(this) } }
        block(deferred.await())
    }

    /**
     * Emit a one-time [UiEvent] to the View.
     * Delivery is guaranteed even if the View is not currently collecting.
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    /**
     * Emit a one-time [NavigationIntent].
     * Delivery is guaranteed even if the View is not currently collecting.
     */
    protected fun navigate(intent: N) {
        viewModelScope.launch { _navigationIntent.send(intent) }
    }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    override fun onCleared() {
        reducerChannel.close()
        super.onCleared()
    }
}
