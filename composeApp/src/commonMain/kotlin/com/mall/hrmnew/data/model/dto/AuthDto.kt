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
    @SerialName("id")
    val id: Int? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("token")
    val token: String? = null
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
