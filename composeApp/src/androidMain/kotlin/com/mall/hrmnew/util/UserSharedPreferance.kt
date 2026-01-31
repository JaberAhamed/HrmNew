package com.mall.hrmnew.util

import android.content.Context
import android.content.SharedPreferences

actual class UserSharedPreference(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    actual fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun setToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }
}