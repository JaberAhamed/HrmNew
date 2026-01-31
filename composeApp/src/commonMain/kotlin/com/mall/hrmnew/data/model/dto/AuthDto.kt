package com.mall.hrmnew.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for login request
 * @property email User's email address
 * @property password User's password
 */
@Serializable
data class LoginRequest(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)

/**
 * Data Transfer Object for login response
 * @property token Authentication token (if returned by API)
 * @property userId User ID (if returned by API)
 * @property message Response message
 * @property success Indicates if login was successful
 */
@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("success")
    val success: Boolean? = null
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
