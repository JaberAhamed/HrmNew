package com.mall.hrmnew.util

expect class UserSharedPreference {
    fun getToken(): String?
    fun setToken(token: String)
    fun clearToken()
}
