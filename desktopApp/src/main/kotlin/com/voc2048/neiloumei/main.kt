package com.voc2048.neiloumei

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NeiLouMei",
    ) {
        App()
    }
}