package com.voc2048.neiloumei.core.state

/**
 * Everything the player can do to the pet. Inputs are values, not callbacks, so the reducer
 * stays pure and every interaction is replayable in a test.
 */
sealed interface GameInput {

    data object Poke : GameInput

    data object Stoke : GameInput

    data object BeginDrag : GameInput

    data object EndDrag : GameInput

    data object Hover : GameInput

    data object Unhover : GameInput
}
