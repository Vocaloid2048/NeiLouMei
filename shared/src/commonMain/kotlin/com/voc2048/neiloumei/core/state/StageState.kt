package com.voc2048.neiloumei.core.state

import com.voc2048.neiloumei.core.model.Action
import com.voc2048.neiloumei.core.model.Mood
import com.voc2048.neiloumei.core.model.Presence
import com.voc2048.neiloumei.core.model.Profile
import com.voc2048.neiloumei.core.model.Temperature

/**
 * The single source of truth published to the renderer.
 *
 * Immutable by design: the renderer observes it, the reducer replaces it. Nothing else may
 * mutate it, which is what keeps the five parallel timelines (drain, expedition, idle, chat
 * stream, random behaviour) from racing each other.
 */
data class StageState(
    val temperature: Temperature = Temperature.Boiling,
    val presence: Presence = Presence.IN_POT,
    val action: Action = Action.IDLE,
    val mood: Mood = Mood(),
    val profile: Profile = Profile(),
    val lastInteractionMillis: Long = 0L,
)
