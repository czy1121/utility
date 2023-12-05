package me.reezy.cosmo.utility.context

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Process
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import kotlin.system.exitProcess

fun Context.meta(key: String): String? {
    try {
        return packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData.getString(key)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

fun Context.resolveCurrentProcessName(): String? {
    val pid = Process.myPid()
    ContextCompat.getSystemService(this, ActivityManager::class.java)?.runningAppProcesses?.forEach {
        if (it.pid == pid) return it.processName
    }
    return null
}

fun Context.isInMainProcess(): Boolean = applicationContext.packageName == resolveCurrentProcessName()



// 当前应用是否可调试
fun Context.isDebuggable(): Boolean = try {
    applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
} catch (e: Exception) {
    e.printStackTrace()
    false
}

// 判断指定包名的应用是否处于前台
fun Context.isForegroundApp(pkgName: String = packageName): Boolean {
    if (pkgName.isBlank()) return false

    ContextCompat.getSystemService(this, ActivityManager::class.java)?.runningAppProcesses?.forEach {
        if (it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return it.processName == pkgName
        }
    }
    return false
}



// 退出当前应用
fun Context.exitApp() {
    ContextCompat.getSystemService(this, ActivityManager::class.java)?.appTasks?.forEach {
        it.finishAndRemoveTask()
    }
    exitProcess(0)
}






