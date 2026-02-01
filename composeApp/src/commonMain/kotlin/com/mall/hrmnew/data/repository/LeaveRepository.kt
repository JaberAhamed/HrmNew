package com.mall.hrmnew.data.repository

import com.mall.hrmnew.data.api.ApiService
import com.mall.hrmnew.data.model.dto.LeaveBalanceResponse

/**
 * Repository for leave related operations
 * This acts as an abstraction layer between the ViewModel and the API service
 * @property apiService ApiService instance for making API calls
 */
class LeaveRepository(
    private val apiService: ApiService
) {

    /**
     * Fetches leave balance data
     * @return Result containing LeaveBalanceResponse on success or Exception on failure
     */
    suspend fun getLeaveBalance(): Result<LeaveBalanceResponse> {
        return try {
            val response = apiService.getLeaveBalance()

            response.onSuccess { balanceResponse ->
                println("Leave balance fetched: ${balanceResponse.message}")
            }.onFailure { error ->
                println("Failed to fetch leave balance: ${error.message}")
            }

            response
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
