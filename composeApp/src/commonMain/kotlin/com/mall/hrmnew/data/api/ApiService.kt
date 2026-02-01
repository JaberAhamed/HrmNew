package com.mall.hrmnew.data.api

import com.mall.hrmnew.data.model.dto.LeaveBalanceResponse
import com.mall.hrmnew.data.model.dto.LoginRequest
import com.mall.hrmnew.data.model.dto.LoginResponse
import com.mall.hrmnew.util.BaseUrlProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headersOf

/**
 * API Service for authentication related operations
 * @property client Ktor HttpClient instance
 */
class ApiService(
    private val client: HttpClient,
    private val baseUrl: String = BaseUrlProvider.getBaseUrl()
) {

    /**
     * Performs user login
     * @param request LoginRequest containing email, password, playerId, and deviceType
     * @return LoginResponse containing authentication data
     */
//    suspend fun login(request: LoginRequest): LoginResponse {
//        return client.post("$baseUrl/app-login") {
//            contentType(ContentType.Application.FormUrlEncoded)
//            setBody(
//                "email=${request.email}" +
//                "&player_id=${request.playerId}" +
//                "&device_type=${request.deviceType}" +
//                "&password=${request.password}"
//            )
//        }.body<LoginResponse>()
//    }
    suspend fun signIn(
        email: String,
        deviceId: String,
        password: String
    ): Result<LoginResponse> = safeApiCall {
        client.post("$baseUrl/v1/login") {
            contentType(ContentType.Application.FormUrlEncoded)
            header("Device-Id", deviceId)
            setBody("email=$email&password=$password")
        }.body()
    }

    suspend fun getLeaveBalance(): Result<LeaveBalanceResponse> = safeApiCall {
        client.get("$baseUrl/v1/leave/balance").body()
    }

    private suspend inline fun <reified T> safeApiCall(
        call: suspend () -> T
    ): Result<T> {
        return try {
            Result.success(call())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
