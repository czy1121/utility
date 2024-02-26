package me.reezy.cosmo.utility.context

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity


@Suppress("UNCHECKED_CAST")
fun <T : Activity> Context.resolveActivity(clazz: Class<T>): T? = when {
    clazz.isInstance(this) -> this as? T
    this is ContextWrapper -> baseContext.resolveActivity(clazz)
    else -> null
}

fun Context.resolveComponentActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.resolveComponentActivity()
    else -> null
}

fun Context.resolveActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.resolveActivity()
    else -> null
}