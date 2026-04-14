package com.datienza.spacex.core.common.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // ------------------------------------------------------------------
    // Minimal concrete implementation used across all tests
    // ------------------------------------------------------------------

    data class State(val count: Int = 0, val label: String = "") : UiState
    sealed interface Event : UiEvent { data class Message(val text: String) : Event }
    sealed interface NavIntent : NavigationIntent { data object GoBack : NavIntent }

    private val sut = object : BaseViewModel<State, Event, NavIntent>(State()) {}

    // ------------------------------------------------------------------
    // UiState tests
    // ------------------------------------------------------------------

    @Test
    fun `initial state is emitted immediately`() = runTest {
        assertEquals(State(), sut.uiState.value)
    }

    @Test
    fun `updateState applies reducer to current state`() = runTest {
        sut.updateState { copy(count = count + 1) }
        sut.uiState.first { it.count == 1 }   // suspends until condition is met
        assertEquals(1, sut.uiState.value.count)
    }

    @Test
    fun `multiple updateState calls are applied in order`() = runTest {
        val iterations = 100
        repeat(iterations) {
            sut.updateState { copy(count = count + 1) }
        }
        // Wait for all reducers to drain
        sut.uiState.first { it.count == iterations }
        assertEquals(iterations, sut.uiState.value.count)
    }

    @Test
    fun `concurrent updateState calls produce consistent final state`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val iterations = 50

        // Fire updates from two concurrent coroutines
        val job1 = launch(dispatcher) { repeat(iterations) { sut.updateState { copy(count = count + 1) } } }
        val job2 = launch(dispatcher) { repeat(iterations) { sut.updateState { copy(count = count + 1) } } }
        job1.join(); job2.join()

        sut.uiState.first { it.count == iterations * 2 }
        assertEquals(iterations * 2, sut.uiState.value.count)
    }

    // ------------------------------------------------------------------
    // UiEvent tests
    // ------------------------------------------------------------------

    @Test
    fun `sendEvent delivers event to collector`() = runTest {
        val collected = mutableListOf<Event>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.uiEvent.toList(collected)
        }

        sut.sendEvent(Event.Message("hello"))
        sut.sendEvent(Event.Message("world"))

        // Give coroutines a chance to process
        testScheduler.advanceUntilIdle()
        job.cancel()

        assertEquals(
            listOf(Event.Message("hello"), Event.Message("world")),
            collected,
        )
    }

    // ------------------------------------------------------------------
    // withState tests
    // ------------------------------------------------------------------

    @Test
    fun `withState sees state after all previously enqueued reducers have settled`() = runTest {
        // Enqueue 3 reducers BEFORE withState is called
        repeat(3) { sut.updateState { copy(count = count + 1) } }

        var observedCount = -1
        sut.withState { state -> observedCount = state.count }

        // withState must have waited for all 3 reducers — count must be 3, not 0 or 1 or 2
        assertEquals(3, observedCount)
    }

    @Test
    fun `withState does not modify state`() = runTest {
        sut.updateState { copy(count = 42) }

        sut.withState { /* read-only */ }

        sut.uiState.first { it.count == 42 }
        assertEquals(42, sut.uiState.value.count)
    }

    // ------------------------------------------------------------------
    // NavigationIntent tests
    // ------------------------------------------------------------------

    @Test
    fun `navigate delivers intent to collector`() = runTest {
        val collected = mutableListOf<NavIntent>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.navigationIntent.toList(collected)
        }

        sut.navigate(NavIntent.GoBack)

        testScheduler.advanceUntilIdle()
        job.cancel()

        assertEquals(listOf(NavIntent.GoBack), collected)
    }
}
