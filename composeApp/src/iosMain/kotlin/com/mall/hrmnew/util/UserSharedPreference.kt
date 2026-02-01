package com.mall.hrmnew.util
import platform.Foundation.NSUserDefaults

actual class UserSharedPreference {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getToken(): String? {
        return userDefaults.stringForKey("token")
    }

    actual fun setToken(token: String) {
        userDefaults.setObject(token, "token")
    }

    actual fun clearToken() {
        userDefaults.removeObjectForKey("token")
    }
}