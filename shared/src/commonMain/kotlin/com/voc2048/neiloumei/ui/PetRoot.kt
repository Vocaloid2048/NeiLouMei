package com.voc2048.neiloumei.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.voc2048.neiloumei.core.clock.GameClock
import com.voc2048.neiloumei.core.state.StageMachine
import com.voc2048.neiloumei.core.state.StageState
import com.voc2048.neiloumei.render.StageCanvas
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.milliseconds

/**
 * Drives the simulation and hands the state to the renderer.
 *
 * The tick interval is 250 ms on purpose: temperature does not need 60 updates per second, and a
 * desktop pet that repaints every frame for eight hours is a battery complaint waiting to happen.
 * Visual animation is the renderer's business; this loop only advances time.
 */
@Composable
fun PetRoot(clock: GameClock, modifier: Modifier = Modifier) {
    var state by remember(clock) { mutableStateOf(StageState()) }

    LaunchedEffect(clock) {
        var last = clock.nowMillis()
        while (isActive) {
            delay(TICK_INTERVAL_MS)
            val now = clock.nowMillis()
            state = StageMachine.advanceWhileRunning(state, (now - last).milliseconds)
            last = now
        }
    }

    Box(modifier.fillMaxSize()) {
        StageCanvas(state = state, modifier = Modifier.fillMaxSize())
    }
}

private const val TICK_INTERVAL_MS = 250L
