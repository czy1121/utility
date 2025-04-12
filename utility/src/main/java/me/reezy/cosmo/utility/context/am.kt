package me.reezy.cosmo.utility.context

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import androidx.core.content.ContextCompat
import kotlin.system.exitProcess

inline val Context.activityManager: ActivityManager? get() = ContextCompat.getSystemService(this, ActivityManager::class.java)

fun Context.isInMainProcess(): Boolean = packageName == resolveCurrentProcessName()

fun Context.resolveCurrentProcessName(): String? {
    val pid = Process.myPid()
    kotlin.runCatching { activityManager?.runningAppProcesses }.getOrNull()?.forEach {
        if (it.pid == pid) return it.processName
    }
    return null
}

// 判断指定包名的应用是否处于前台
fun Context.isForegroundApp(pkgName: String = packageName): Boolean {
    if (pkgName.isBlank()) return false

    kotlin.runCatching { activityManager?.runningAppProcesses }.getOrNull()?.forEach {
        if (it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return it.processName == pkgName
        }
    }
    return false
}


fun Context.exitTasks() {
    activityManager?.appTasks?.forEach {
        it.finishAndRemoveTask()
    }
}

// 退出当前应用
fun Context.exitApp() {
    exitTasks()
    exitProcess(0)
}






