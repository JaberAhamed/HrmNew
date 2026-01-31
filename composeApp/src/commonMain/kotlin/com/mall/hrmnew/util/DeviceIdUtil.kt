package com.mall.hrmnew.util

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Generates a unique device identifier
 * For common code, this generates a random UUID
 * Platform-specific implementations can override this to provide
 * a more persistent device ID
 */
@OptIn(ExperimentalUuidApi::class)
fun getDeviceId(): String {
    return Uuid.random().toString()
}
