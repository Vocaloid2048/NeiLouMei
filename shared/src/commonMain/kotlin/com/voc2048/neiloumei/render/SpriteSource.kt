package com.voc2048.neiloumei.render

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Reads sprite bytes from wherever the platform keeps them.
 *
 * Decoding is intentionally *not* part of this interface. Each platform decodes with its own
 * native path (Skia on desktop, UIKit on iOS) and returns a ready [ImageBitmap], because the
 * alternative — handing raw bytes to a common decoder — is exactly what makes sprite animation
 * stall on the main thread.
 *
 * Design note: sprites are loaded from **external files**, not from `composeResources/drawable`.
 * Two reasons:
 *  1. ~100 frames would generate ~100 typed accessors, and adding an action would mean editing
 *     generated-looking code by hand.
 *  2. Art is AI-generated and iterated constantly; reading from disk at launch means replacing a
 *     PNG does not require a full Kotlin rebuild.
 *
 * The blocking risk is handled by loading everything eagerly once at startup, then indexing into
 * an in-memory map at frame time. Nothing decodes during the animation loop.
 */
interface SpriteSource {

    /** Returns encoded bytes for [spriteId], or null when absent. */
    suspend fun read(spriteId: String): ByteArray?

    /** Every sprite id this source can serve, used to warm the cache at launch. */
    suspend fun availableIds(): List<String>
}

/**
 * Placeholder until the art pipeline produces real frames.
 * Kept here so the renderer's dependency is already expressed.
 */
interface SpriteDecoder {
    suspend fun decode(bytes: ByteArray): ImageBitmap?
}
