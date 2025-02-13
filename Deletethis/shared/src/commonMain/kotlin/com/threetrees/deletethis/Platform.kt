package com.threetrees.deletethis

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform