package com.voc2048.neiloumei

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform