package com.mall.hrmnew.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * Android implementation of getCurrentInstant
 */
actual fun getCurrentInstant(): Instant {
    return Clock.System.now()
}
