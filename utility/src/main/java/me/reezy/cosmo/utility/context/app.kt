package me.reezy.cosmo.utility.context

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

// 当前应用是否可调试
fun Context.isDebuggable(): Boolean = try {
    applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
} catch (e: Exception) {
    e.printStackTrace()
    false
}

fun Context.meta(key: String): String? = try {
    applicationInfo.metaData.getString(key)
} catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
}








