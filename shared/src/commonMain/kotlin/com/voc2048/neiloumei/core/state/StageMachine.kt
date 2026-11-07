package com.voc2048.neiloumei.core.state

import com.voc2048.neiloumei.core.model.Action
import com.voc2048.neiloumei.core.model.Presence
import com.voc2048.neiloumei.core.model.Temperature
import kotlin.time.Duration

/**
 * The whole simulation, expressed as two pure functions.
 *
 * No coroutines, no timers, no side effects. A caller drives this by feeding elapsed time and
 * player input; tests drive it by feeding eight hours of `delta` in one call. This is the single
 * most important structural decision in the project — see `agent-files/design.md` §6.
 */
object StageMachine {

    /** Degrees lost per hour while the app is running and the egg is at home. */
    const val DRAIN_PER_HOUR_RUNNING = 8f

    /** Faster, because nobody is stoking while the egg is away. */
    const val DRAIN_PER_HOUR_AWAY = 12f

    /** Slowest, because a closed app insulates the pot. */
    const val DRAIN_PER_HOUR_CLOSED = 3f

    /** Heat added by one stoke. */
    const val STOKE_GAIN = 12f

    /** Contentment lost per hour of neglect; deliberately slow. */
    const val BOREDOM_PER_HOUR = 0.05f

    private const val MILLIS_PER_HOUR = 3_600_000.0

    /**
     * Advances the simulation by real elapsed time while the app is foregrounded.
     */
    fun advanceWhileRunning(state: StageState, delta: Duration): StageState =
        advance(
            state = state,
            delta = delta,
            drainPerHour = if (state.presence == Presence.AWAY) {
                DRAIN_PER_HOUR_AWAY
            } else {
                DRAIN_PER_HOUR_RUNNING
            },
        )

    /**
     * Advances by wall-clock time that passed while the app was closed.
     * Same shape, different rate — call this once at launch with the elapsed gap.
     */
    fun advanceWhileClosed(state: StageState, delta: Duration): StageState =
        advance(state, delta, DRAIN_PER_HOUR_CLOSED)

    fun advance(state: StageState, delta: Duration, drainPerHour: Float): StageState {
        if (delta <= Duration.ZERO) return state

        val hours = delta.inWholeMilliseconds / MILLIS_PER_HOUR
        val drained = state.temperature.celsius - (hours * drainPerHour).toFloat()
        val nextTemperature = Temperature.of(drained, state.profile.maxTemperature)
        val boredom = (hours * BOREDOM_PER_HOUR).toFloat()

        return state.copy(
            temperature = nextTemperature,
            mood = state.mood.neglected(boredom),
        )
    }

    /**
     * Applies a player input. Kept separate from [advance] so input never has to be modelled as
     * a zero-length tick.
     */
    fun apply(state: StageState, input: GameInput, nowMillis: Long): StageState = when (input) {
        GameInput.Poke -> state.copy(
            action = Action.POKE,
            mood = state.mood.poked(),
            lastInteractionMillis = nowMillis,
        )

        GameInput.Stoke -> state.copy(
            action = Action.STOKE,
            temperature = Temperature.of(
                celsius = state.temperature.celsius + STOKE_GAIN,
                max = state.profile.maxTemperature,
            ),
            mood = state.mood.stoked(),
            lastInteractionMillis = nowMillis,
        )

        GameInput.BeginDrag -> state.copy(
            action = Action.DRAG,
            lastInteractionMillis = nowMillis,
        )

        GameInput.EndDrag -> state.copy(
            action = Action.DROP,
            lastInteractionMillis = nowMillis,
        )

        GameInput.Hover -> state.copy(action = Action.HOVER)

        GameInput.Unhover -> state.copy(
            action = if (state.action == Action.HOVER) Action.IDLE else state.action,
        )
    }

    /**
     * The clip to show when nothing transient is happening. Called by the scheduler, not by the
     * renderer.
     */
    fun restingAction(state: StageState): Action = when {
        state.presence == Presence.AWAY -> Action.IDLE
        state.temperature.band == com.voc2048.neiloumei.core.model.TemperatureBand.COLD -> Action.SAD
        state.temperature.band == com.voc2048.neiloumei.core.model.TemperatureBand.BOIL &&
            state.mood.contentment > 0.7f -> Action.HAPPY
        else -> Action.IDLE
    }
}
