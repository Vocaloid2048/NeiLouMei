package com.voc2048.neiloumei.core.model

/**
 * Where the egg currently is. Orthogonal to [Temperature]: the pot looks the same whether the
 * egg is home or not, which is what lets the renderer stay static while the egg moves.
 */
enum class Presence {
    /** In the pot mouth. */
    IN_POT,

    /** Somewhere on the desktop, pot stays put. */
    ROAMING,

    /** Off-screen on an expedition; the pot is alone and draining faster. */
    AWAY,
}

/**
 * A playable clip, not a render state. The renderer maps this to a clip name and a rig;
 * the state machine never knows about pixels.
 */
enum class Action {
    IDLE,
    HOVER,
    POKE,
    DRAG,
    DROP,
    SLEEP,
    THINK,
    TALK,
    STOKE,
    ROLL,
    OPEN,
    HAPPY,
    SAD,
    SPECIAL,
}
