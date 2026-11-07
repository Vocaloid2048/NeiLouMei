package com.voc2048.neiloumei

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.voc2048.neiloumei.core.clock.GameClock
import com.voc2048.neiloumei.render.Metrics

/**
 * Desktop container.
 *
 * `transparent` and `undecorated` must be set together on Compose Desktop — a transparent
 * decorated window throws. Click-through is not wired yet; that is the D1 spike.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "你滷味",
        transparent = true,
        undecorated = true,
        alwaysOnTop = true,
        resizable = false,
        state = rememberWindowState(
            width = Metrics.WINDOW_SIZE_DP.dp,
            height = Metrics.WINDOW_SIZE_DP.dp,
        ),
    ) {
        App(clock = GameClock { System.currentTimeMillis() })
    }
}
