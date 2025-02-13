package com.threetrees.gamenotes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform