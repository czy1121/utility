@file:Suppress("UNCHECKED_CAST")

package me.reezy.cosmo.utility

import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import kotlin.system.exitProcess

var Context.clipboardPrimaryText: String
    get() = ContextCompat.getSystemService(this, ClipboardManager::class.java)?.primaryClip?.let {
        if (it.itemCount > 0) it.getItemAt(0).text.toString() else null
    } ?: ""
    set(content) {
        ContextCompat.getSystemService(this, ClipboardManager::class.java)?.setPrimaryClip(ClipData.newPlainText(null, content))
    }

fun Context.resolveComponentActivity(): ComponentActivity? = resolveActivity(ComponentActivity::class.java)

fun <T: Activity> Context.resolveActivity(clazz: Class<T>): T? {
    var obj: Context? = this
    do {
        if (clazz.isInstance(obj)) return obj as T
        obj = if (obj is ContextWrapper) obj.baseContext else null
    } while (obj != null)
    return obj
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

// 判断指定包名的应用是否系统应用
fun Context.isSystemApp(pkgName: String = packageName) = pkgName.isNotBlank() && try {
    packageManager.getApplicationInfo(pkgName, 0).flags and ApplicationInfo.FLAG_SYSTEM > 0
} catch (e: PackageManager.NameNotFoundException) {
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
@RequiresPermission(value = "android.permission.KILL_BACKGROUND_PROCESSES")
fun Context.exitApp() {
    ContextCompat.getSystemService(this, ActivityManager::class.java)?.apply {
        appTasks.forEach {
            it.finishAndRemoveTask()
        }
        killBackgroundProcesses(packageName)
    }
    exitProcess(0)
}






