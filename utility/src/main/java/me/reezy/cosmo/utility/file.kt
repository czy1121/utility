package me.reezy.cosmo.utility

import android.content.Context
import android.content.res.AssetManager
import java.io.*
import java.net.URLConnection
import java.text.DecimalFormat


fun AssetManager.readText(filename: String): String? {
    try {
        return String(open(filename).readBytes())
    } catch (_: FileNotFoundException) {
    } catch (_: IOException) {
    }
    return null
}

fun Context.readText(filename: String): String? {
    try {
        return String(openFileInput(filename).readBytes())
    } catch (_: FileNotFoundException) {
    } catch (_: IOException) {
    }
    return null
}

fun Context.writeText(filename: String, text: String) {
    writeBytes(filename, text.toByteArray())
}

fun Context.writeBytes(filename: String, bytes: ByteArray, retry: Boolean = true) {
    try {
        ByteArrayInputStream(bytes).copyTo(openFileOutput(filename, Context.MODE_PRIVATE))
    } catch (e: FileNotFoundException) {
        if (retry) {
            writeBytes(filename, bytes, false)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

}

/** 获取文件 MimeType */
fun File.mimeType(): String = inputStream().buffered().use {
    return URLConnection.guessContentTypeFromStream(it)
}

/** 清空目录 */
fun File.clean() {
    if (!exists() || !isDirectory) {
        return
    }
    listFiles()?.forEach {
        if (it.isDirectory) {
            it.clean()
        } else {
            it.delete()
        }
    }
}


/** 计算目录或文件大小 */
fun File.size(): Long {
    if (isFile) return length()

    return try {
        listFiles()?.sumOf { if (it.isDirectory) it.size() else it.length() } ?: 0
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}


