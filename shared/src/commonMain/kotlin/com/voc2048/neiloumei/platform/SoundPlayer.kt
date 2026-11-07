package com.voc2048.neiloumei.platform

/**
 * Platform capability, injected at the composition root.
 *
 * An interface rather than `expect/actual` on purpose: `commonTest` cannot substitute an
 * `actual`, so anything expressed that way becomes untestable. Implementations live in the
 * platform source sets (`jvmMain`, `iosMain`).
 */
interface SoundPlayer {

    fun play(id: SoundId)

    /** Boiling ambience. Off by default; the player opts in. */
    fun setAmbient(enabled: Boolean)
}

enum class SoundId {
    POKE,
    SPLASH,
    BLIP,
    RETURN,
    ROLL,
    STOKE,
    CHEST,
    DEPART,
}

/** No-op implementation for previews and tests. */
object SilentSoundPlayer : SoundPlayer {
    override fun play(id: SoundId) = Unit
    override fun setAmbient(enabled: Boolean) = Unit
}
