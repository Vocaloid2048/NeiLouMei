package com.voc2048.neiloumei.core.model

/**
 * Persistent player-facing identity and progression. Everything here survives a restart.
 */
data class Profile(
    val name: String = DEFAULT_NAME,
    val level: Int = 1,
    val broth: Int = 0,
    val cracks: Int = 0,
) {

    /** Each crack costs 10 degrees of ceiling, down to a floor so the pot can never be dead. */
    val maxTemperature: Float
        get() = (Temperature.MAX - cracks * CRACK_COST).coerceAtLeast(MIN_MAX_TEMPERATURE)

    companion object {
        const val DEFAULT_NAME = "阿茶"
        const val CRACK_COST = 10f
        const val MIN_MAX_TEMPERATURE = 20f
    }
}
