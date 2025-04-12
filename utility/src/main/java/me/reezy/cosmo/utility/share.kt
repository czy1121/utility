package me.reezy.cosmo.utility

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import me.reezy.cosmo.utility.extension.setGrantFile
import java.io.File


fun Context.shareText(text: String, title: String = "分享") {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(intent, title))
}

fun Context.shareImage(image: File, title: String = "分享") {
    val intent = Intent(Intent.ACTION_SEND)
    intent.setGrantFile(this, "image/jpeg", image)
    startActivity(Intent.createChooser(intent, title))
}


const val SEND_TO_WHATSAPP = "com.whatsapp"
const val SEND_TO_LINE = "jp.naver.line.android"


fun Context.sendText(toPackageName: String, message: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage(resolve(this, intent, toPackageName))
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(intent)
    } catch (ex: Throwable) {
        Log.e("OoO.share", "share failed, please confirm whether the app is installed")
    }
}

fun Context.sendImage(toPackageName: String, image: File) {
    try {
        val type = "image/jpeg"
        val intent = Intent(Intent.ACTION_SEND).setType(type)
        intent.setPackage(resolve(this, intent, toPackageName))
        intent.setGrantFile(this, type, image)
        startActivity(intent)
    } catch (ex: Throwable) {
        Log.e("OoO.share", "share failed, please confirm whether the app is installed")
    }
}



@SuppressLint("QueryPermissionsNeeded")
private fun resolve(context: Context, share: Intent, searchKey: String): String? {
    try {
        for (info in context.packageManager.queryIntentActivities(share, 0)) {
            if (info.activityInfo.packageName.lowercase().contains(searchKey) || info.activityInfo.name.lowercase().contains(searchKey)) {
                return info.activityInfo.packageName
            }
        }
    } catch (e: Exception) {
        Log.e("OoO.share", "Failed to resolve packages for sharing.. defaulting to any. " + e.message)
    }
    return searchKey
}