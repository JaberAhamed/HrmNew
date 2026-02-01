package com.mall.hrmnew.util

import android.content.Context
import android.provider.Settings

/**
 * Android implementation of getDeviceId
 * Uses Settings.Secure.ANDROID_ID which is unique per device
 * and persists across app installations (unless app is factory reset)
 */
actual fun getDeviceId(): String {
    val context = getContext()
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

private var cachedContext: Context? = null

fun setContext(context: Context) {
    cachedContext = context
}

fun getContext(): Context {
    return cachedContext ?: throw IllegalStateException("Context not set. Call setContext() in Application or MainActivity first.")
}
