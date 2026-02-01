package com.mall.hrmnew.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for login request
 * @property email User's email address
 * @property playerId Unique player/device identifier
 * @property deviceType Device type (e.g., "android", "ios")
 * @property password User's password
 */
@Serializable
data class LoginRequest(
    @SerialName("email")
    val email: String,

    @SerialName("player_id")
    val playerId: String,

    @SerialName("device_type")
    val deviceType: String,

    @SerialName("password")
    val password: String
)

/**
 * Data Transfer Object for login response
 * @property id User ID (if returned by API)
 * @property message Response message
 * @property token Authentication token (if returned by API)
 */
@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    @SerialName("status_code")
    val statusCode: Int,
    val data: LoginData? = null
)

@Serializable
data class LoginData(
    val user: User,
    val token: String,
    @SerialName("token_type")
    val tokenType: String
)

@Serializable
data class User(
    val id: Int,
    @SerialName("employee_id")
    val employeeId: Int? = null,
    val name: String,
    val email: String,
    val type: String,
    val avatar: String? = null
)

/**
 * Data Transfer Object for API error response
 * @property message Error message
 * @property errors Validation errors map (field name -> error message)
 */
@Serializable
data class ErrorResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("errors")
    val errors: Map<String, String>? = null
)
