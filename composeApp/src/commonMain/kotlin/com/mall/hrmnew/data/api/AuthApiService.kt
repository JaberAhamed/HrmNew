package com.mall.hrmnew.data.api

import com.mall.hrmnew.data.model.dto.LoginRequest
import com.mall.hrmnew.data.model.dto.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

/**
 * API Service for authentication related operations
 * @property client Ktor HttpClient instance
 */
class AuthApiService(
    private val client: HttpClient
) {

    /**
     * Performs user login
     * @param request LoginRequest containing email and password
     * @param deviceId Unique device identifier to be sent in headers
     * @return LoginResponse containing authentication data
     */
    suspend fun login(
        request: LoginRequest,
        deviceId: String
    ): LoginResponse {
        return client.post {
            url {
                protocol = io.ktor.http.URLProtocol.HTTPS
                host = "dailyattend.com"
                path("api", "v1", "login")
            }
            header("device_id", deviceId)
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<LoginResponse>()
    }
}
