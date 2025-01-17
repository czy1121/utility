@file:Suppress("DEPRECATION")

package me.reezy.cosmo.utility.context

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build


fun Context.isPackageInstalled(pkgName: String): Boolean {
    return packageManager.getLaunchIntentForPackage(pkgName) != null
}

@SuppressLint("PackageManagerGetSignatures")
fun Context.getSignHashCode() = getPackageInfo(packageName, PackageManager.GET_SIGNATURES)?.signatures?.get(0)?.hashCode() ?: 0


@SuppressLint("QueryPermissionsNeeded")
fun Context.getResolveInfo(intent: Intent, flags: Int = 0): List<ResolveInfo> = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(flags.toLong()))
    } else {
        packageManager.queryIntentActivities(intent, flags)
    }
} catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    listOf()
}

fun Context.getApplicationInfo(pkgName: String, flags: Int = 0): ApplicationInfo? = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getApplicationInfo(pkgName, PackageManager.ApplicationInfoFlags.of(flags.toLong()))
    } else {
        packageManager.getApplicationInfo(pkgName, flags)
    }
} catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
}

fun Context.getPackageInfo(pkgName: String, flags: Int = 0): PackageInfo? = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(pkgName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        packageManager.getPackageInfo(pkgName, flags)
    }
} catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
}


// 判断指定包名的应用是否在系统分区
fun Context.isPackageInSystemImage(pkgName: String = packageName): Boolean {
    val info = getApplicationInfo(pkgName) ?: return false
    return info.flags and ApplicationInfo.FLAG_SYSTEM > 0
}

fun Context.isPackageExist(pkgName: String): Boolean = getPackageInfo(pkgName) != null

fun Context.isIntentAvailable(intent: Intent): Boolean {
    return getResolveInfo(intent, PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()
}

/** 获取应用名称 */
fun Context.getApplicationLabel(pkgName: String = packageName): String? {
    val info = getApplicationInfo(pkgName) ?: return null
    return packageManager.getApplicationLabel(info).toString()
}

/** 获取应用图标 */
fun Context.getApplicationIcon(pkgName: String = packageName): Drawable? {
    try {
        return packageManager.getApplicationIcon(pkgName)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/** 获取应用版本名 */
fun Context.getVersionName(pkgName: String = packageName) = getPackageInfo(pkgName)?.versionName ?: ""

/** 获取应用版本号 */
fun Context.getVersionCode(pkgName: String = packageName): Long {
    val info = getPackageInfo(pkgName) ?: return -1
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) info.longVersionCode else info.versionCode.toLong()
}

