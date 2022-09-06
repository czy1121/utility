package me.reezy.cosmo.utility

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import me.reezy.cosmo.utility.extension.compressCircularly
import me.reezy.cosmo.utility.extension.setGrantFile
import java.io.File

object Share {
    const val TO_WHATSAPP = "com.whatsapp"
    const val TO_LINE = "jp.naver.line.android"


    fun sendText(context: Context, toPackageName: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage(resolve(context, intent, toPackageName))
            intent.putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(intent)
        } catch (ex: Throwable) {
            Log.e("Shareable", "Sharing failed, please confirm whether the app is installed")
        }
    }

    fun sendImage(context: Context, toPackageName: String, image: File) {
        try {
            val type = "image/jpeg"
            val intent = Intent(Intent.ACTION_SEND).setType(type)
            intent.setPackage(resolve(context, intent, toPackageName))
            intent.setGrantFile(context, type, image)
            context.startActivity(intent)
        } catch (ex: Throwable) {
            Log.e("Shareable", "Sharing failed, please confirm whether the app is installed")
        }
    }

    fun sendBitmap(context: Context, toPackageName: String, bitmap: Bitmap) {
        val bytes = bitmap.compressCircularly(300 * 1024, Bitmap.CompressFormat.JPEG) ?: return
        bitmap.recycle()

        val file = File(context.filesDir, "images/share.jpeg")
        file.parentFile?.mkdirs()
        file.delete()
        file.writeBytes(bytes)

        sendImage(context, toPackageName, file)
    }

    private fun resolve(context: Context, share: Intent, searchKey: String): String? {
        try {
            for (info in context.packageManager.queryIntentActivities(share, 0)) {
                if (info.activityInfo.packageName.lowercase().contains(searchKey) || info.activityInfo.name.lowercase().contains(searchKey)) {
                    return info.activityInfo.packageName
                }
            }
        } catch (e: Exception) {
            Log.e("Shareable", "Failed to resolve packages for sharing.. defaulting to any. " + e.message)
        }
        return searchKey
    }
}