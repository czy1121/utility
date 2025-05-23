@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility.delegate

import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import me.reezy.cosmo.utility.asBoolean
import me.reezy.cosmo.utility.asDouble
import me.reezy.cosmo.utility.asFloat
import me.reezy.cosmo.utility.asInt
import me.reezy.cosmo.utility.asLong
import me.reezy.cosmo.utility.asString
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun argumentInt(default: Int = 0, key: String? = null) = ArgumentField(default, key, Any::asInt)
inline fun argumentLong(default: Long = 0, key: String? = null) = ArgumentField(default, key, Any::asLong)
inline fun argumentFloat(default: Float = 0f, key: String? = null) = ArgumentField(default, key, Any::asFloat)
inline fun argumentDouble(default: Double = 0.0, key: String? = null) = ArgumentField(default, key, Any::asDouble)
inline fun argumentBoolean(default: Boolean = false, key: String? = null) = ArgumentField(default, key, Any::asBoolean)
inline fun argumentString(default: String = "", key: String? = null) = ArgumentField(default, key, Any::asString)


@RestrictTo(RestrictTo.Scope.LIBRARY)
class ArgumentField<T>(private val default: T, private val key: String?, private val cast: Any.() -> T?) : ReadOnlyProperty<Any, T> {
    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (thisRef is Fragment) {
            thisRef.arguments?.get(key ?: property.name)?.cast() ?: default
        }
        return default
    }
}
