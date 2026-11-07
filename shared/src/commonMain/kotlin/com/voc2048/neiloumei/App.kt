package com.voc2048.neiloumei

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.voc2048.neiloumei.core.clock.GameClock
import com.voc2048.neiloumei.ui.PetRoot

/**
 * Shared entry point for every platform. Deliberately takes the clock as a parameter: the
 * composition root owns time, not the UI.
 */
@Composable
fun App(clock: GameClock) {
    PetRoot(clock = clock, modifier = Modifier.fillMaxSize())
}
