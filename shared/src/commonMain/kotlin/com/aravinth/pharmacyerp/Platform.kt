package com.aravinth.pharmacyerp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform