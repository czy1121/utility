package me.reezy.cosmo.utility

import kotlin.random.Random

fun randomString(length: Int, source: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"): String {
    val result = StringBuilder(length)
    for (i in 0 until length) {
        result.append(source[Random.nextInt(source.length)])
    }
    return result.toString()
}

fun <T : Any> randomOneOf(array: Array<T>): T {
    return array[Random.nextInt(array.size - 1)]
}

fun <T : Any> randomShuffle(array: Array<T>): Boolean {
    val size = array.size
    for (i in 1 until array.size - 1) {
        val random = Random.nextInt(size - i)
        val temp = array[size - i]
        array[size - i] = array[random]
        array[random] = temp
    }
    return true
}