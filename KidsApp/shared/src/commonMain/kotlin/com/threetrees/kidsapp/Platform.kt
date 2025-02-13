package com.threetrees.kidsapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform