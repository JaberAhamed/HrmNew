package com.mall.hrmnew.data.repository

import com.mall.hrmnew.data.api.AuthApiService
import com.mall.hrmnew.data.model.dto.LoginRequest
import com.mall.hrmnew.data.model.dto.LoginResponse

/**
 * Repository for authentication related operations
 * This acts as an abstraction layer between the ViewModel and the API service
 * @property apiService AuthApiService instance for making API calls
 */
class AuthRepository(
    private val apiService: AuthApiService
) {

    /**
     * Performs user login
     * @param email User's email address
     * @param password User's password
     * @param deviceId Unique device identifier
     * @return Result containing LoginResponse on success or Exception on failure
     */
    suspend fun login(
        email: String,
        password: String,
        deviceId: String
    ): Result<LoginResponse> {
        return try {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = apiService.login(request, deviceId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
