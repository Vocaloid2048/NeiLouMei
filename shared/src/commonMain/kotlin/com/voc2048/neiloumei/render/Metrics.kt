package com.voc2048.neiloumei.render

/**
 * Stage geometry, in source-canvas pixels (1024 x 1024).
 *
 * Mirrors `agent-files/asset-spec.md` §2. Every magic number in this file has a counterpart in
 * the art spec; if one moves, the other must move with it. Do not inline these values elsewhere.
 */
object Metrics {

    /** Source canvas edge length. All coordinates below are in this space. */
    const val CANVAS = 1024f

    // --- Pot -----------------------------------------------------------------
    const val POT_LEFT = 102f
    const val POT_TOP = 300f
    const val POT_DIAMETER = 820f
    const val POT_HEIGHT = 640f
    const val POT_CORNER = 120f

    // --- Pot mouth (also the liquid ellipse) ---------------------------------
    const val MOUTH_CENTER_X = 512f
    const val MOUTH_CENTER_Y = 430f
    const val MOUTH_RX = 300f
    const val MOUTH_RY = 105f

    // --- Egg -----------------------------------------------------------------
    const val EGG_WIDTH = 330f
    const val EGG_HEIGHT = 440f

    /** Bottom-centre pivot; the egg is placed by this point, never by its top-left corner. */
    const val EGG_PIVOT_X = 512f
    const val EGG_PIVOT_Y = 520f

    // --- Window --------------------------------------------------------------
    const val WINDOW_SIZE_DP = 280
}
