package com.mall.hrmnew.util

import kotlinx.datetime.Instant

/**
 * Platform-specific clock utility
 * Returns the current instant in time
 */
expect fun getCurrentInstant(): Instant
