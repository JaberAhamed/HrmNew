package com.mall.hrmnew.data.network

import com.mall.hrmnew.util.UserSharedPreference
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

/**
 * Factory object for creating and configuring the HTTP client
 * This ensures a single instance of HttpClient is used throughout the app
 */
object ApiClient {

    private var instance: HttpClient? = null
    private val mutex = Mutex()

    /**
     * Creates and configures the HttpClient with necessary plugins
     * @param userSharedPreference UserSharedPreference instance for getting auth token
     * @return Configured HttpClient instance
     */
    suspend fun getInstance(userSharedPreference: UserSharedPreference): HttpClient {
        if (instance == null) {
            mutex.withLock {
                if (instance == null) {
                    instance = create(userSharedPreference)
                }
            }
        }
        return instance!!
    }

    fun reset() {
        instance?.close()
        instance = null
    }

    /**
     * Creates and configures the HttpClient with necessary plugins
     * @param userSharedPreference UserSharedPreference instance for getting auth token
     * @return Configured HttpClient instance
     */
    private fun create(userSharedPreference: UserSharedPreference): HttpClient {
        return HttpClient {
            // Install ContentNegotiation plugin for JSON serialization/deserialization
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    useAlternativeNames = false
                })
            }

            // Install Logging plugin for debugging
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor: $message")
                    }
                }
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
        }
    }
}
