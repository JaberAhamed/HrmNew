package com.mall.hrmnew.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaveBalanceResponse(
    val success: Boolean,
    val message: String,
    @SerialName("status_code")
    val statusCode: Int,
    val data: LeaveBalanceData? = null
)

@Serializable
data class LeaveBalanceData(
    @SerialName("leave_balances")
    val leaveBalances: List<LeaveBalance>,
    val totals: LeaveTotals,
    val year: Int
)

@Serializable
data class LeaveBalance(
    @SerialName("leave_type")
    val leaveType: LeaveType,
    @SerialName("allocated_days")
    val allocatedDays: Int,
    @SerialName("used_days")
    val usedDays: Int,
    @SerialName("remaining_days")
    val remainingDays: Int,
    @SerialName("carried_forward")
    val carriedForward: Int,
    val year: String
)

@Serializable
data class LeaveType(
    val id: Int,
    val name: String
)

@Serializable
data class LeaveTotals(
    @SerialName("total_allocated")
    val totalAllocated: Int,
    @SerialName("total_used")
    val totalUsed: Int,
    @SerialName("total_remaining")
    val totalRemaining: Int,
    @SerialName("total_carried_forward")
    val totalCarriedForward: Int
)
