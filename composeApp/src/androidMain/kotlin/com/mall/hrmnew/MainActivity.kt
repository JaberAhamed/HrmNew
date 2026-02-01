package com.mall.hrmnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mall.hrmnew.permissions.LocationPermissionController
import com.mall.hrmnew.ui.appinterface.AppExit
import com.mall.hrmnew.util.UserSharedPreference
import com.mall.hrmnew.util.setContext

class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionController: LocationPermissionController
    private lateinit var userSharedPreference: UserSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        locationPermissionController = LocationPermissionController()
        locationPermissionController.initializeLaunchers(this)
        LocationPermissionController.setCurrentActivity(this)

        userSharedPreference = UserSharedPreference(this)
        setContext(this)

        val appExit = AndroidAppExit(this)
        setContent {
            App(
                appExit = appExit,
                permissionController = locationPermissionController,
                userSharedPreference = userSharedPreference
            )
        }
    }
}

class PreviewAppExit : AppExit {
    override fun exit() {
        // no-op for preview
    }
}

// Preview is commented out because UserSharedPreference requires Context
// @Preview
// @Composable
// fun AppAndroidPreview() {
//     val permissionController = LocationPermissionController()
//     App(appExit = PreviewAppExit(), permissionController = permissionController, userSharedPreference = ???)
// }