package com.mall.hrmnew

import androidx.compose.ui.window.ComposeUIViewController
import com.mall.hrmnew.permissions.LocationPermissionController
import com.mall.hrmnew.ui.appinterface.AppExit
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        val appExit = IosAppExit()
        val permissionController = LocationPermissionController()
        App(appExit = appExit, permissionController = permissionController)
    }
}

class IosAppExit : AppExit {
    override fun exit() {
        // iOS doesn't support exiting the app programmatically
        // This is a no-op for iOS
    }
}
