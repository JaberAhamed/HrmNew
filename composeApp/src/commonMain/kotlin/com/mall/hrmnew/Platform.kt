package com.mall.hrmnew

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform