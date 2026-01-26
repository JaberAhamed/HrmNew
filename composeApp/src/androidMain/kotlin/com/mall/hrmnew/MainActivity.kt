package com.mall.hrmnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mall.hrmnew.ui.appinterface.AppExit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val appExit = AndroidAppExit(this)
        setContent {
            App(appExit = appExit)
        }
    }
}

class PreviewAppExit : AppExit {
    override fun exit() {
        // no-op for preview
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

    App(appExit = PreviewAppExit())
}