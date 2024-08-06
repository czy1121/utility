package me.reezy.cosmo.utility.view

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner


fun View.getLifecycleOwner(): LifecycleOwner {
    if (this is LifecycleOwner) return this
    val owner = findViewTreeLifecycleOwner()
    if (owner != null) return owner
    return getContextLifecycleOwner() ?: throw Exception("can not find LifecycleOwner")
}

fun View.getContextLifecycleOwner(): LifecycleOwner? {
    var o: Context? = context
    do {
        if (o is LifecycleOwner) return o
        o = if (o is ContextWrapper) o.baseContext else null
    } while (o != null)
    return null
}