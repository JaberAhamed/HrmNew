package com.mall.hrmnew.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory object for creating and configuring the HTTP client
 * This ensures a single instance of HttpClient is used throughout the app
 */
object HttpClientFactory {

    /**
     * Base URL for the API
     */
    const val BASE_URL = "https://dailyattend.com/"

    /**
     * Creates and configures the HttpClient with necessary plugins
     * @return Configured HttpClient instance
     */
    fun create(): HttpClient {
        return HttpClient {
            // Install ContentNegotiation plugin for JSON serialization/deserialization
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
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
        }
    }
}
