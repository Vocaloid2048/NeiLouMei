package com.voc2048.neiloumei.render

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.voc2048.neiloumei.core.model.TemperatureBand
import com.voc2048.neiloumei.core.state.StageState

/**
 * Placeholder renderer: flat shapes instead of sprites.
 *
 * This exists so the window, the frame clock and the reducer can be exercised before a single
 * image is generated. When the art arrives, only the four `draw*` functions below are replaced by
 * `drawImage` calls — the layering order and the coordinate space stay identical.
 */
@Composable
fun StageCanvas(state: StageState, modifier: Modifier = Modifier) {
    val wobble by rememberInfiniteTransition(label = "idle").animateFloat(
        initialValue = -7f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2600),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "eggBob",
    )

    Canvas(modifier) {
        val scale = size.minDimension / Metrics.CANVAS

        drawPot(scale)
        drawLiquid(scale, state.temperature.band)
        drawEgg(scale, state, bob = wobble)
        drawRim(scale)
    }
}

private fun DrawScope.drawPot(scale: Float) {
    drawRoundRect(
        color = PotBody,
        topLeft = Offset(Metrics.POT_LEFT * scale, Metrics.POT_TOP * scale),
        size = Size(Metrics.POT_DIAMETER * scale, Metrics.POT_HEIGHT * scale),
        cornerRadius = CornerRadius(Metrics.POT_CORNER * scale),
    )
}

private fun DrawScope.drawLiquid(scale: Float, band: TemperatureBand) {
    drawOval(
        color = liquidColor(band),
        topLeft = Offset(
            (Metrics.MOUTH_CENTER_X - Metrics.MOUTH_RX) * scale,
            (Metrics.MOUTH_CENTER_Y - Metrics.MOUTH_RY) * scale,
        ),
        size = Size(Metrics.MOUTH_RX * 2f * scale, Metrics.MOUTH_RY * 2f * scale),
    )
}

private fun DrawScope.drawEgg(scale: Float, state: StageState, bob: Float) {
    val left = (Metrics.EGG_PIVOT_X - Metrics.EGG_WIDTH / 2f) * scale
    val top = (Metrics.EGG_PIVOT_Y - Metrics.EGG_HEIGHT + bob) * scale

    drawOval(
        color = eggColor(state.temperature.band),
        topLeft = Offset(left, top),
        size = Size(Metrics.EGG_WIDTH * scale, Metrics.EGG_HEIGHT * scale),
    )
}

private fun DrawScope.drawRim(scale: Float) {
    drawOval(
        color = PotRim,
        topLeft = Offset(
            (Metrics.MOUTH_CENTER_X - Metrics.MOUTH_RX) * scale,
            (Metrics.MOUTH_CENTER_Y - Metrics.MOUTH_RY) * scale,
        ),
        size = Size(Metrics.MOUTH_RX * 2f * scale, Metrics.MOUTH_RY * 2f * scale),
        style = Stroke(width = 10f * scale),
    )
}

private fun liquidColor(band: TemperatureBand): Color = when (band) {
    TemperatureBand.BOIL -> Color(0xFF8A4B16)
    TemperatureBand.COOL -> Color(0xFF5A3418)
    TemperatureBand.COLD -> Color(0xFF3A2A20)
}

private fun eggColor(band: TemperatureBand): Color = when (band) {
    TemperatureBand.BOIL -> Color(0xFFC98F5A)
    TemperatureBand.COOL -> Color(0xFFB47F52)
    TemperatureBand.COLD -> Color(0xFF8A7563)
}

private val PotBody = Color(0xFF2E2A26)
private val PotRim = Color(0xFF4A4239)
