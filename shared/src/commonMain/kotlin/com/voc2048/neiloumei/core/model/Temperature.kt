package com.voc2048.neiloumei.core.model

/**
 * Pot heat, 0..100.
 *
 * Invariant: always within [MIN, max]. Construct through [of] so no consumer has to re-clamp.
 */
@JvmInline
value class Temperature private constructor(val celsius: Float) {

    val band: TemperatureBand
        get() = when {
            celsius >= BOIL_THRESHOLD -> TemperatureBand.BOIL
            celsius >= COOL_THRESHOLD -> TemperatureBand.COOL
            else -> TemperatureBand.COLD
        }

    companion object {
        const val MIN = 0f
        const val MAX = 100f

        /** At or above this band the liquid bubbles and steams. */
        const val BOIL_THRESHOLD = 70f

        /** Below this band the pot is cold enough for expedition settlement to be punitive. */
        const val COOL_THRESHOLD = 10f

        val Boiling: Temperature get() = Temperature(MAX)
        val RoomTemperature: Temperature get() = Temperature(MIN)

        fun of(celsius: Float, max: Float = MAX): Temperature =
            Temperature(celsius.coerceIn(MIN, max.coerceAtLeast(MIN)))
    }
}

enum class TemperatureBand { BOIL, COOL, COLD }
