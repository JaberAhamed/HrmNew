package com.mall.hrmnew.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

actual class LocationPermissionController {
    private val activity: Activity?
        get() = currentActivity.get()

    private val _foregroundLocationGranted: MutableState<Boolean> = mutableStateOf(
        checkForegroundLocationPermission()
    )

    private val _backgroundLocationGranted: MutableState<Boolean> = mutableStateOf(
        checkBackgroundLocationPermission()
    )

    actual val foregroundLocationGranted: State<Boolean> = _foregroundLocationGranted
    actual val backgroundLocationGranted: State<Boolean> = _backgroundLocationGranted

    private var foregroundPermissionLauncher: ActivityResultLauncher<String>? = null
    private var backgroundPermissionLauncher: ActivityResultLauncher<String>? = null

    private var pendingForegroundResult: ((Boolean) -> Unit)? = null
    private var pendingBackgroundResult: ((Boolean) -> Unit)? = null

    actual fun checkForegroundLocationPermission(): Boolean {
        val context = activity ?: return false
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    actual fun checkBackgroundLocationPermission(): Boolean {
        val context = activity ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Background location not required for Android < 10
        }
    }

    actual fun requestForegroundLocationPermission(onResult: (Boolean) -> Unit) {
        pendingForegroundResult = onResult
        foregroundPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        ?: onResult(false)
    }

    actual fun requestBackgroundLocationPermission(onResult: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pendingBackgroundResult = onResult
            backgroundPermissionLauncher?.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            ?: onResult(false)
        } else {
            onResult(true)
        }
    }

    actual fun requestBothPermissions(onResult: (Boolean) -> Unit) {
        // First request foreground permission
        requestForegroundLocationPermission { foregroundGranted ->
            if (foregroundGranted) {
                // Then request background permission
                requestBackgroundLocationPermission { backgroundGranted ->
                    onResult(backgroundGranted)
                }
            } else {
                onResult(false)
            }
        }
    }

    actual fun refreshPermissionStatus() {
        _foregroundLocationGranted.value = checkForegroundLocationPermission()
        _backgroundLocationGranted.value = checkBackgroundLocationPermission()
    }

    actual fun initializeLaunchers(activity: Any) {
        val act = activity as? ComponentActivity ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foregroundPermissionLauncher = act.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                _foregroundLocationGranted.value = isGranted
                pendingForegroundResult?.invoke(isGranted)
                pendingForegroundResult = null
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            backgroundPermissionLauncher = act.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                _backgroundLocationGranted.value = isGranted
                pendingBackgroundResult?.invoke(isGranted)
                pendingBackgroundResult = null
            }
        }
    }

    companion object {
        private var currentActivity = WeakReference<Activity>(null)

        fun setCurrentActivity(activity: Activity) {
            currentActivity = WeakReference(activity)
        }
    }
}
