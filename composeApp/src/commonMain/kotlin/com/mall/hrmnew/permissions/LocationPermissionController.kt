package com.mall.hrmnew.permissions

import androidx.compose.runtime.State

expect class LocationPermissionController() {

    val foregroundLocationGranted: State<Boolean>

    val backgroundLocationGranted: State<Boolean>

    fun checkForegroundLocationPermission(): Boolean
    fun checkBackgroundLocationPermission(): Boolean

    fun requestForegroundLocationPermission(onResult: (Boolean) -> Unit)
    fun requestBackgroundLocationPermission(onResult: (Boolean) -> Unit)

    fun requestBothPermissions(onResult: (Boolean) -> Unit)

    fun refreshPermissionStatus()

    fun initializeLaunchers(activity: Any)
}

// Extension property to check if both permissions are granted
val LocationPermissionController.bothPermissionsGranted: Boolean
    get() = foregroundLocationGranted.value && backgroundLocationGranted.value
