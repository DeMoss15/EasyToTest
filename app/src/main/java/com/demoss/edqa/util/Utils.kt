package com.demoss.edqa.util

fun generateKey(length: Int): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map { charPool[it] }
        .joinToString(EmptyConstants.EMPTY_STRING)
}