package com.voc2048.neiloumei.core.clock

/**
 * Wall-clock access, injected rather than read directly.
 *
 * Every duration the game reasons about (temperature drain, expedition countdown, offline
 * accrual) must be derived from a clock supplied at the boundary. Without this the whole
 * `core` package is untestable, because tests would have to actually wait.
 */
fun interface GameClock {
    fun nowMillis(): Long
}
