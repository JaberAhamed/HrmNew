package com.mall.hrmnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mall.hrmnew.permissions.LocationPermissionController
import com.mall.hrmnew.ui.appinterface.AppExit

class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionController: LocationPermissionController

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        locationPermissionController = LocationPermissionController()
        locationPermissionController.initializeLaunchers(this)
        LocationPermissionController.setCurrentActivity(this)

        val appExit = AndroidAppExit(this)
        setContent {
            App(appExit = appExit, permissionController = locationPermissionController)
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
    val permissionController = LocationPermissionController()
    App(appExit = PreviewAppExit(), permissionController = permissionController)
}