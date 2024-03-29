package me.reezy.cosmo.utility.context

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build


// 判断指定包名的应用是否在系统分区
fun Context.isPackageInSystemImage(pkgName: String = packageName) = kotlin.runCatching {
    packageManager.getApplicationInfo(pkgName, 0).flags and ApplicationInfo.FLAG_SYSTEM > 0
}.getOrDefault(false)

fun Context.isPackageInstalled(pkgName: String): Boolean {
    return pkgName.isNotBlank() && packageManager.getLaunchIntentForPackage(pkgName) != null
}

fun Context.isPackageExist(pkgName: String): Boolean {
    return getPackageInfo(pkgName) != null
}

fun Context.getPackageInfo(pkgName: String = packageName): PackageInfo? {
    try {
        return packageManager.info(pkgName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}


/**
 * 获取应用的版本名称
 *
 * @param pkgName 包名
 * @return 应用版本名称 ""表示失败
 */
fun Context.getVersionName(pkgName: String = packageName): String {
    return kotlin.runCatching { packageManager.info(pkgName, 0).versionName }.getOrDefault("")
}

/**
 * 获取应用的版本号
 *
 * @param pkgName 包名
 * @return 应用的版本号 -1表示失败
 */
fun Context.getVersionCode(pkgName: String = packageName): Long {
    if (pkgName.isBlank()) return -1
    return try {
        val info = packageManager.info(pkgName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) info.longVersionCode else info.versionCode.toLong()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        -1
    }
}

@SuppressLint("PackageManagerGetSignatures")
fun Context.getSingerHashCode() = packageManager.info(packageName, PackageManager.GET_SIGNATURES).signatures[0].hashCode()


fun Context.getApplicationLabel(pkgName: String = packageName): String? {
    try {
        val pi = packageManager.info(pkgName, PackageManager.GET_CONFIGURATIONS)
        return packageManager.getApplicationLabel(pi.applicationInfo).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}


fun Context.getApplicationIcon(pkgName: String = packageName): Drawable? {
    try {
        return packageManager.getApplicationIcon(pkgName)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

fun Context.isIntentAvailable(intent: Intent): Boolean {
    return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()
}


private fun PackageManager.info(pkgName: String, flags: Int): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(pkgName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION")
        getPackageInfo(pkgName, flags)
    }
}