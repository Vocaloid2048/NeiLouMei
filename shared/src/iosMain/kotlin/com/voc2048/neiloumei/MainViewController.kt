package com.voc2048.neiloumei

import androidx.compose.ui.window.ComposeUIViewController
import com.voc2048.neiloumei.core.clock.GameClock
import platform.Foundation.NSDate

fun MainViewController() = ComposeUIViewController {
    App(clock = GameClock { (NSDate().timeIntervalSince1970 * 1000.0).toLong() })
}
