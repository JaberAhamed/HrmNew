package com.mall.hrmnew.data.repository

import com.mall.hrmnew.data.api.ApiService
import com.mall.hrmnew.data.model.dto.LoginRequest
import com.mall.hrmnew.data.model.dto.LoginResponse

/**
 * Repository for authentication related operations
 * This acts as an abstraction layer between the ViewModel and the API service
 * @property apiService AuthApiService instance for making API calls
 */
class AuthRepository(
    private val apiService: ApiService
) {

    /**
     * Performs user login
     * @param email User's email address
     * @param password User's password
     * @param playerId Unique player/device identifier
     * @param deviceType Device type (e.g., "android", "ios")
     * @return Result containing LoginResponse on success or Exception on failure
     */
    suspend fun signIn(
        email: String,
        deviceId: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            // Validate input
            if (email.isBlank()) {
                return Result.failure(IllegalArgumentException("Email cannot be empty"))
            }
            if (password.isBlank()) {
                return Result.failure(IllegalArgumentException("Password cannot be empty"))
            }
            if (deviceId.isBlank()) {
                return Result.failure(IllegalArgumentException("Player ID cannot be empty"))
            }

            // Call API Service
            val response = apiService.signIn(
                email = email,
                deviceId = deviceId,
                password = password
            )

            // Process response
            response.onSuccess { signInResponse ->
                println("SignIn successful: ${signInResponse.message}")
            }.onFailure { error ->
                println("SignIn failed: ${error.message}")
            }

            response
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
