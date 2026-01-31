package com.mall.hrmnew.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * iOS implementation of getCurrentInstant
 */
actual fun getCurrentInstant(): Instant {
    // Use NSDate for iOS native time access
    val timestamp = NSDate().timeIntervalSince1970
    return Instant.fromEpochMilliseconds((timestamp * 1000).toLong())
}
