package com.mall.hrmnew.util

import platform.UIKit.UIDevice

/**
 * iOS implementation of getDeviceId
 * Uses UIDevice.current.identifierForVendor which is unique per device
 * and persists until all apps from the vendor are uninstalled
 */
actual fun getDeviceId(): String {
    return UIDevice.currentDevice.identifierForVendor?.toString() ?: ""
}
