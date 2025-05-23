@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility.delegate

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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


inline fun extraInt(default: Int = 0, key: String? = null) = ExtraField(default, key, Any::asInt)
inline fun extraLong(default: Long = 0, key: String? = null) = ExtraField(default, key, Any::asLong)
inline fun extraFloat(default: Float = 0f, key: String? = null) = ExtraField(default, key, Any::asFloat)
inline fun extraDouble(default: Double = 0.0, key: String? = null) = ExtraField(default, key, Any::asDouble)
inline fun extraBoolean(default: Boolean = false, key: String? = null) = ExtraField(default, key, Any::asBoolean)
inline fun extraString(default: String = "", key: String? = null) = ExtraField(default, key, Any::asString)

inline fun <reified T : Parcelable> extraParcelable(default: T, key: String? = null) = ExtraParcelable(T::class.java, default, key)


internal val Any.extras: Bundle? get() = when (this) {
    is Activity -> intent?.extras
    is Fragment -> activity?.intent?.extras
    else -> null
}

class ExtraField<T>(private val default: T, private val key: String?, private val cast: Any.() -> T?) : ReadOnlyProperty<Any, T> {
    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return thisRef.extras?.get(key ?: property.name)?.cast() ?: default
    }
}


@RestrictTo(RestrictTo.Scope.LIBRARY)
class ExtraParcelable<T : Parcelable>(private val clazz: Class<T>, private val default: T, private val key: String? = null) : ReadOnlyProperty<Any, T> {
    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        val bundle = thisRef.extras ?: return default
        val field = key ?: property.name
        return if (Build.VERSION.SDK_INT >= 34) {
            bundle.getParcelable(field, clazz) ?: default
        } else {
            val parcelable = bundle.getParcelable<T>(field)
            if (clazz.isInstance(parcelable)) {
                parcelable!!
            } else {
                default
            }
        }
    }
}