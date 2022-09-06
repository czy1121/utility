package me.reezy.cosmo.utility.extension

import kotlin.random.Random

fun Random.nextString(length: Int, source: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"): String {
    val result = StringBuilder(length)
    for (i in 0 until length) {
        result.append(source[Random.nextInt(source.length)])
    }
    return result.toString()
}
