package me.reezy.cosmo.utility

import kotlin.reflect.KType
import kotlin.reflect.typeOf

object Json {
    interface Serializer {
        fun <T> encode(type: KType, value: T): String
        fun <T> decode(type: KType, string: String): T?
    }

    lateinit var serializer: Serializer

    inline fun <reified T> encode(value: T): String {
        return serializer.encode(typeOf<T>(), value)
    }

    inline fun <reified T> decode(string: String): T? {
        return serializer.decode(typeOf<T>(), string)
    }
}