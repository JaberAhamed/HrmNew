package com.mall.hrmnew.util

/**
 * Generates a unique device identifier
 * Platform-specific implementations provide a persistent device ID:
 * - Android: Uses Settings.Secure.ANDROID_ID
 * - iOS: Uses UIDevice.current.identifierForVendor
 */
expect fun getDeviceId(): String
