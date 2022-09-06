package me.reezy.cosmo.utility.context

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Context.resolveComponentActivity(): ComponentActivity? = resolveActivity(ComponentActivity::class.java)

@Suppress("UNCHECKED_CAST")
fun <T : Activity> Context.resolveActivity(clazz: Class<T>): T? {
    var obj: Context? = this
    do {
        if (clazz.isInstance(obj)) return obj as T
        obj = if (obj is ContextWrapper) obj.baseContext else null
    } while (obj != null)
    return obj
}