package com.voc2048.neiloumei.core

import com.voc2048.neiloumei.core.model.Action
import com.voc2048.neiloumei.core.model.Presence
import com.voc2048.neiloumei.core.model.Profile
import com.voc2048.neiloumei.core.model.Temperature
import com.voc2048.neiloumei.core.model.TemperatureBand
import com.voc2048.neiloumei.core.state.GameInput
import com.voc2048.neiloumei.core.state.StageMachine
import com.voc2048.neiloumei.core.state.StageState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class StageMachineTest {

    @Test
    fun drains_eight_degrees_per_hour_while_running() {
        val after = StageMachine.advanceWhileRunning(StageState(), 1.hours)
        assertEquals(92f, after.temperature.celsius, 0.01f)
    }

    @Test
    fun drains_faster_while_the_egg_is_away() {
        val away = StageState(presence = Presence.AWAY)
        val after = StageMachine.advanceWhileRunning(away, 1.hours)
        assertEquals(88f, after.temperature.celsius, 0.01f)
    }

    @Test
    fun drains_slowest_while_the_app_is_closed() {
        val after = StageMachine.advanceWhileClosed(StageState(), 8.hours)
        assertEquals(76f, after.temperature.celsius, 0.01f)
    }

    @Test
    fun never_drops_below_zero() {
        val after = StageMachine.advanceWhileRunning(StageState(), 20.hours)
        assertEquals(0f, after.temperature.celsius, 0.001f)
        assertEquals(TemperatureBand.COLD, after.temperature.band)
    }

    @Test
    fun band_thresholds_match_the_art_spec() {
        assertEquals(TemperatureBand.BOIL, Temperature.of(70f).band)
        assertEquals(TemperatureBand.COOL, Temperature.of(69.9f).band)
        assertEquals(TemperatureBand.COOL, Temperature.of(10f).band)
        assertEquals(TemperatureBand.COLD, Temperature.of(9.9f).band)
    }

    @Test
    fun stoking_adds_twelve_degrees() {
        val mid = StageState(temperature = Temperature.of(50f))
        val after = StageMachine.apply(mid, GameInput.Stoke, nowMillis = 1_000L)
        assertEquals(62f, after.temperature.celsius, 0.01f)
        assertEquals(Action.STOKE, after.action)
        assertEquals(1_000L, after.lastInteractionMillis)
    }

    @Test
    fun cracks_lower_the_ceiling() {
        val cracked = StageState(
            temperature = Temperature.of(75f),
            profile = Profile(cracks = 2),
        )
        val after = StageMachine.apply(cracked, GameInput.Stoke, nowMillis = 0L)
        assertEquals(80f, after.temperature.celsius, 0.01f)
    }

    @Test
    fun poking_raises_contentment() {
        val before = StageState().mood.contentment
        val after = StageMachine.apply(StageState(), GameInput.Poke, nowMillis = 0L)
        assertTrue(after.mood.contentment > before)
        assertEquals(Action.POKE, after.action)
    }

    @Test
    fun neglect_lowers_contentment_slowly() {
        val start = StageState()
        val after = StageMachine.advanceWhileRunning(start, 4.hours)
        val drop = start.mood.contentment - after.mood.contentment
        assertEquals(0.2f, drop, 0.001f)
    }

    @Test
    fun resting_action_follows_the_band() {
        val cold = StageState(temperature = Temperature.of(4f))
        assertEquals(Action.SAD, StageMachine.restingAction(cold))

        val boiling = StageState(temperature = Temperature.Boiling)
        assertEquals(Action.IDLE, StageMachine.restingAction(boiling))
    }

    @Test
    fun zero_delta_is_a_no_op() {
        val state = StageState()
        assertEquals(state, StageMachine.advance(state, 0.minutes, 8f))
    }
}
