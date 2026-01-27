package com.mall.hrmnew.permissions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorized
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

actual class LocationPermissionController {
    private val locationManager = CLLocationManager()

    private val _foregroundLocationGranted: MutableState<Boolean> = mutableStateOf(
        checkForegroundLocationPermission()
    )

    private val _backgroundLocationGranted: MutableState<Boolean> = mutableStateOf(
        checkBackgroundLocationPermission()
    )

    actual val foregroundLocationGranted: State<Boolean> = _foregroundLocationGranted
    actual val backgroundLocationGranted: State<Boolean> = _backgroundLocationGranted

    actual fun checkForegroundLocationPermission(): Boolean {
        val status = locationManager.authorizationStatus()
        return status == kCLAuthorizationStatusAuthorized ||
                status == kCLAuthorizationStatusAuthorizedWhenInUse ||
                status == kCLAuthorizationStatusAuthorizedAlways
    }

    actual fun checkBackgroundLocationPermission(): Boolean {
        val status = locationManager.authorizationStatus()
        return status == kCLAuthorizationStatusAuthorizedAlways
    }

    actual fun requestForegroundLocationPermission(onResult: (Boolean) -> Unit) {
        locationManager.requestWhenInUseAuthorization()
        // iOS handles permission via delegate callback, so we check after a delay
        onResult(checkForegroundLocationPermission())
    }

    actual fun requestBackgroundLocationPermission(onResult: (Boolean) -> Unit) {
        locationManager.requestAlwaysAuthorization()
        onResult(checkBackgroundLocationPermission())
    }

    actual fun requestBothPermissions(onResult: (Boolean) -> Unit) {
        locationManager.requestAlwaysAuthorization()
        onResult(checkBackgroundLocationPermission())
    }

    actual fun refreshPermissionStatus() {
        _foregroundLocationGranted.value = checkForegroundLocationPermission()
        _backgroundLocationGranted.value = checkBackgroundLocationPermission()
    }

    actual fun initializeLaunchers(activity: Any) {
        // No-op for iOS - permissions are handled differently
    }
}
