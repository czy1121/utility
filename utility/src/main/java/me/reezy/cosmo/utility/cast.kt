package me.reezy.cosmo.utility


fun Any.asInt(): Int? = when (this) {
    is Int -> this
    is Number -> toInt()
    is Boolean -> if (this) 1 else 0
    is String -> toIntOrNull()
    else -> null
}

fun Any.asLong(): Long? = when (this) {
    is Long -> this
    is Number -> toLong()
    is Boolean -> if (this) 1 else 0
    is String -> toLongOrNull()
    else -> null
}


fun Any.asFloat(): Float? = when (this) {
    is Float -> this
    is Number -> toFloat()
    is Boolean -> if (this) 1f else 0f
    is String -> toFloatOrNull()
    else -> null
}

fun Any.asDouble(): Double? = when (this) {
    is Double -> this
    is Number -> toDouble()
    is Boolean -> if (this) 1.0 else 0.0
    is String -> toDoubleOrNull()
    else -> null
}


fun Any.asBoolean(): Boolean? = when (this) {
    is Boolean -> this
    is Number -> toInt() != 0
    is String -> when(lowercase()) {
        "true", "yes", "on", "1" -> true
        "false", "no", "off", "0" -> false
        else -> null
    }
    else -> null
}

fun Any.asString(): String? = when (this) {
    is String -> this
    is Number -> toString()
    is Boolean -> toString()
    else -> null
}

