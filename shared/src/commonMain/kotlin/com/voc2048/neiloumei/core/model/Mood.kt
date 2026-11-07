package com.voc2048.neiloumei.core.model

/**
 * The character's reaction to how the pot is being managed.
 *
 * Deliberately separate from [Temperature]: temperature is a resource the player spends
 * attention on, mood is what the egg thinks about it. Never derive one from the other, or the
 * player will eventually see a boiling pot with a miserable egg.
 */
data class Mood(
    val contentment: Float = DEFAULT_CONTENTMENT,
    val affection: Float = 0f,
) {

    fun poked() = copy(
        contentment = (contentment + 0.02f).coerceIn(0f, 1f),
        affection = (affection + 0.01f).coerceIn(0f, 1f),
    )

    fun stoked() = copy(contentment = (contentment + 0.05f).coerceIn(0f, 1f))

    fun neglected(amount: Float) = copy(contentment = (contentment - amount).coerceIn(0f, 1f))

    companion object {
        const val DEFAULT_CONTENTMENT = 0.5f
    }
}
