package com.mall.hrmnew.example// shared/src/commonMain/kotlin/com/sj/hrm/network/ApiClient.kt

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

expect class UserSharedPreference {
    fun getToken(): String?
}

object BaseUrlProvider {
    fun getBaseUrl(): String = "https://api.example.com/api/"
}

class ApiClient(
    private val baseUrl: String = BaseUrlProvider.getBaseUrl(),
    private val userSharedPreference: UserSharedPreference
) {
    companion object {
        private var instance: HttpClient? = null
        private val mutex = Mutex()

        suspend fun getInstance(userSharedPreference: UserSharedPreference): HttpClient {
            if (instance == null) {
                mutex.withLock {
                    if (instance == null) {
                        instance = createHttpClient(userSharedPreference)
                    }
                }
            }
            return instance!!
        }

        fun reset() {
            instance?.close()
            instance = null
        }

        private fun createHttpClient(userSharedPreference: UserSharedPreference): HttpClient {
            return HttpClient {
                // Content Negotiation
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                    })
                }

                // Logging
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.BODY
                }

                // Default headers and request customization
                install(DefaultRequest) {
                    header("Accept", "application/json")
                    header("Content-Type", "application/json")

                    val token = userSharedPreference.getToken()
                    if (token != null) {
                        header("Authorization", token)
                    }
                }

                // Timeout configuration
                install(HttpTimeout) {
                    requestTimeoutMillis = 30000
                    connectTimeoutMillis = 30000
                    socketTimeoutMillis = 30000
                }

                // HttpClient engine is platform-specific (configured in actual/expect)
            }
        }
    }
}

// shared/src/commonMain/kotlin/com/sj/hrm/network/ApiService.kt

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

class ApiService(
    private val httpClient: HttpClient,
    private val baseUrl: String = BaseUrlProvider.getBaseUrl()
) {
    
    // Login
    suspend fun doSignIn(
        email: String,
        playerId: String,
        deviceType: String,
        password: String
    ): Result<LoginResponse> = safeApiCall {
        httpClient.post("$baseUrl/app-login") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("email=$email&player_id=$playerId&device_type=$deviceType&password=$password")
        }.body()
    }

   // Other Api Call


    private suspend inline fun <reified T> safeApiCall(call: suspend () -> T): Result<T> {
        return try {
            Result.success(call())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// shared/src/commonMain/kotlin/com/sj/hrm/network/Models.kt

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int?,
    val message: String?,
    val token: String?
)




// shared/src/androidMain/kotlin/com/sj/hrm/network/UserSharedPreference.android.kt

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

// shared/src/iosMain/kotlin/com/sj/hrm/network/UserSharedPreference.ios.kt

import platform.Foundation.NSUserDefaults

actual class UserSharedPreference {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getToken(): String? {
        return userDefaults.stringForKey("token")
    }

    fun setToken(token: String) {
        userDefaults.setObject(token, "token")
    }

    fun clearToken() {
        userDefaults.removeObjectForKey("token")
    }
}
