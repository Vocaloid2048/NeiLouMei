package com.voc2048.neiloumei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.voc2048.neiloumei.core.clock.GameClock

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(clock = GameClock { System.currentTimeMillis() })
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(clock = GameClock { 0L })
}
